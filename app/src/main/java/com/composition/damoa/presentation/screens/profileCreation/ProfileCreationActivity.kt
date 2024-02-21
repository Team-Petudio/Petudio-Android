package com.composition.damoa.presentation.screens.profileCreation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
import com.composition.damoa.presentation.common.extensions.onDefault
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.common.extensions.reduceImageSizeAndCreateFile
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.common.utils.PhotoPicker
import com.composition.damoa.presentation.common.utils.permissionRequester.Permission
import com.composition.damoa.presentation.common.utils.permissionRequester.PermissionRequester
import com.composition.damoa.presentation.screens.login.LoginActivity
import com.composition.damoa.presentation.screens.profileCreation.ProfileCreationViewModel.Companion.KEY_CONCEPT_ID
import com.composition.damoa.presentation.screens.profileCreation.component.ProfileCreationScreen
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.INVALID_PET_IMAGE_SIZE
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.NETWORK_ERROR
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.PAYMENT_FAILED_LACK_OF_TICKET
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.PAYMENT_SUCCESS
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.PET_DETECT_SUCCESS
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.TOKEN_EXPIRED
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.UNKNOWN_ERROR
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.UPLOAD_PET_SUCCESS
import com.composition.damoa.presentation.screens.ticketPurchase.TicketPurchaseActivity
import com.esafirm.imagepicker.model.Image
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ProfileCreationActivity : ComponentActivity() {
    private val viewModel: ProfileCreationViewModel by viewModels()
    private val permissionRequester = PermissionRequester()
    private val photoPicker = PhotoPicker(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ProfileCreationScreen(
                viewModel = viewModel,
                navController = navController,
                onPhotoUploadClick = ::launchStoragePermissionRequester,
            )

            viewModel.event.collectEvent(navController)
        }
    }

    private fun SharedFlow<ProfileCreationUiEvent>.collectEvent(navController: NavHostController) {
        onUi {
            collectLatest { event ->
                when (event) {
                    PAYMENT_SUCCESS -> navController.navigate(ProfileCreationScreen.PAYMENT_RESULT.route)
                    INVALID_PET_IMAGE_SIZE -> showToast(R.string.pet_photo_size_invalid_message)
                    PET_DETECT_SUCCESS -> navController.navigate(ProfileCreationScreen.PHOTO_UPLOAD_RESULT.route)
                    UPLOAD_PET_SUCCESS -> navController.navigate(ProfileCreationScreen.PAYMENT.route)
                    NETWORK_ERROR -> showToast(R.string.network_failure_message)
                    UNKNOWN_ERROR -> showToast(R.string.unknown_error_message)
                    PAYMENT_FAILED_LACK_OF_TICKET -> {
                        TicketPurchaseActivity.startActivity(this@ProfileCreationActivity)
                        finish()
                    }

                    TOKEN_EXPIRED -> {
                        LoginActivity.startActivity(this@ProfileCreationActivity)
                        finish()
                    }
                }
            }
        }
    }

    private fun launchStoragePermissionRequester() {
        permissionRequester.launch(
            context = this,
            permission = Permission.READ_EXTERNAL_STORAGE,
            dialogMessage = getString(R.string.read_external_storage_message),
            onGranted = ::launchPhotoPicker,
            onDenied = { showToast(getString(R.string.read_external_storage_permission_denied_message)) },
        )
    }

    private fun launchPhotoPicker() {
        photoPicker.launchPhotoPicker(
            context = this,
            maxSelectSize = viewModel.selectedImageUiState.value.canMoreSelectPhotoSize,
        ) photoPicker@{ images ->
            if (images.isEmpty()) return@photoPicker
            reduceImageSizeAndDetect(images)
        }
    }

    private fun reduceImageSizeAndDetect(images: List<Image>) {
        if (!viewModel.validatePetImageSize(images)) return
        viewModel.changeToImageSelectLoading()

        onDefault {
            val resizedImageFiles = images.map { image ->
                async { reduceImageSizeAndCreateFile(image.uri, image.id.toString()) }
            }.awaitAll()
            viewModel.detectPetImages(resizedImageFiles.filterNotNull())
        }
    }

    companion object {
        fun startActivity(context: Context, conceptId: Long) {
            val intent = Intent(context, ProfileCreationActivity::class.java)
                .putExtra(KEY_CONCEPT_ID, conceptId)
            context.startActivity(intent)
        }
    }
}
