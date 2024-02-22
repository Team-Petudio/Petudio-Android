package com.composition.damoa.presentation.screens.profileCreation

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
import com.composition.damoa.presentation.common.extensions.onDefault
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.common.extensions.requestReadExternalStoragePermission
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.common.utils.PhotoPicker
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
import com.composition.damoa.presentation.screens.store.StoreActivity
import com.esafirm.imagepicker.model.Image
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.quality
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


@AndroidEntryPoint
class ProfileCreationActivity : ComponentActivity() {
    private val viewModel: ProfileCreationViewModel by viewModels()
    private val photoPicker = PhotoPicker(this)
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            ProfileCreationScreen(
                viewModel = viewModel,
                navController = navController,
                onPhotoUploadClick = this::requestReadExternalStoragePermission,
            )
        }

        viewModel.event.collectEvent()
    }

    private fun SharedFlow<ProfileCreationUiEvent>.collectEvent() {
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
                        StoreActivity.startActivity(this@ProfileCreationActivity)
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

    private fun requestReadExternalStoragePermission() {
        requestReadExternalStoragePermission(
            message = getString(R.string.read_external_storage_message),
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

    private suspend fun Context.reduceImageSizeAndCreateFile(
        contentUri: Uri,
        fileName: String,
        quality: Int = 80,
        imageWidth: Int = 512,
    ): File? = runCatching {
        val imageFile = requireNotNull(createFileFromContentUri(contentUri, "$fileName.jpg")) {
            "[ERROR] content uri로부터 File을 생성하는데 실패했습니다."
        }
        val originalBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(contentUri))
        val imageHeight = (originalBitmap.height * (imageWidth.toFloat() / originalBitmap.width)).toInt()

        Compressor.compress(this, imageFile) {
            quality(quality)
            default(width = imageWidth, height = imageHeight)
        }
    }.getOrNull()

    private fun Context.createFileFromContentUri(contentUri: Uri, fileName: String): File? {
        var inputStream: InputStream? = null
        var outputStream: FileOutputStream? = null
        val newFile = File(filesDir, fileName)

        try {
            inputStream = contentResolver.openInputStream(contentUri)
            outputStream = FileOutputStream(newFile)

            inputStream?.copyTo(outputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            inputStream?.close()
            outputStream?.close()
        }

        return newFile
    }


    companion object {
        fun startActivity(context: Context, conceptId: Long) {
            val intent = Intent(context, ProfileCreationActivity::class.java)
                .putExtra(KEY_CONCEPT_ID, conceptId)
            context.startActivity(intent)
        }
    }
}
