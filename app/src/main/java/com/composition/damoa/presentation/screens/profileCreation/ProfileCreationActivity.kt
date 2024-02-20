package com.composition.damoa.presentation.screens.profileCreation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.common.extensions.onDefault
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.common.extensions.reduceImageSizeAndCreateFile
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.common.utils.permissionRequester.Permission
import com.composition.damoa.presentation.common.utils.permissionRequester.PermissionRequester
import com.composition.damoa.presentation.screens.login.LoginActivity
import com.composition.damoa.presentation.screens.profileCreation.ProfileCreationViewModel.Companion.KEY_CONCEPT_ID
import com.composition.damoa.presentation.screens.profileCreation.screen.payment.PaymentScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.paymentResult.PaymentResultScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petColor.PetColorScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petName.PetNameScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.PetPhotoSelectionScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.photoUploadIntroduce.PhotoUploadIntroduceScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.photoUploadResult.PhotoUploadResultScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.profileCreationIntroduce.ProfileCreationIntroduceScreen
import com.composition.damoa.presentation.screens.profileCreation.state.ConceptDetailUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PaymentUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetInfoUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetPhotoSelectionUiState
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.INVALID_PET_IMAGE_SIZE
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.NETWORK_ERROR
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.PAYMENT_FAILED_LACK_OF_TICKET
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.PAYMENT_SUCCESS
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.PET_DETECT_SUCCESS
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.TOKEN_EXPIRED
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.UNKNOWN_ERROR
import com.composition.damoa.presentation.screens.profileCreation.state.ProfileCreationUiEvent.UPLOAD_PET_SUCCESS
import com.composition.damoa.presentation.screens.profileCreation.state.SelectedImageUiState
import com.composition.damoa.presentation.screens.ticketPurchase.TicketPurchaseActivity
import com.composition.damoa.presentation.ui.theme.PetudioTheme
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
            ProfileCreation(
                viewModel = viewModel,
                onPhotoUploadClick = ::launchStoragePermissionRequester,
                navController = navController,
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
            context.startActivity(getIntent(context, conceptId))
        }

        private fun getIntent(
            context: Context,
            conceptId: Long,
        ): Intent = Intent(context, ProfileCreationActivity::class.java)
            .putExtra(KEY_CONCEPT_ID, conceptId)
    }
}

@Composable
private fun ProfileCreation(
    viewModel: ProfileCreationViewModel,
    onPhotoUploadClick: () -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    PetudioTheme {
        val activity = LocalContext.current as? ComponentActivity
        val conceptDetailUiState by viewModel.conceptDetailUiState.collectAsStateWithLifecycle()
        val petPhotoSelectionUiState by viewModel.petPhotoSelectionUiState.collectAsStateWithLifecycle()
        val petUiState by viewModel.petInfoUiState.collectAsStateWithLifecycle()
        val selectedImageUiState by viewModel.selectedImageUiState.collectAsStateWithLifecycle()
        val ticketUiState by viewModel.paymentUiState.collectAsStateWithLifecycle()

        Scaffold(
            topBar = {
                ProfileCreationTopAppBar(
                    onNavigationClick = { if (!navController.popBackStack()) activity?.finish() },
                )
            },
        ) { padding ->
            ProfileCreationNavHost(
                modifier = Modifier.padding(top = padding.calculateTopPadding()),
                navController = navController,
                conceptDetailUiState = conceptDetailUiState,
                petPhotoSelectionUiState = petPhotoSelectionUiState,
                petInfoUiState = petUiState,
                onPetNameChanged = viewModel::updatePetName,
                onPetColorSelected = viewModel::updateColor,
                onPetUploadClick = viewModel::uploadPetImages,
                onPhotoUploadClick = onPhotoUploadClick,
                selectedImageUiState = selectedImageUiState,
                paymentUiState = ticketUiState,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProfileCreationTopAppBar(onNavigationClick: () -> Unit = {}) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_back),
                    tint = Color.Black,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
    )
}

@Composable
private fun ProfileCreationNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: ProfileCreationScreen = ProfileCreationScreen.PROFILE_CREATION_INTRODUCE,
    conceptDetailUiState: ConceptDetailUiState,
    petPhotoSelectionUiState: PetPhotoSelectionUiState,
    petInfoUiState: PetInfoUiState,
    onPetNameChanged: (String) -> Unit,
    onPetColorSelected: (PetColor) -> Unit,
    onPetUploadClick: () -> Unit,
    onPhotoUploadClick: () -> Unit,
    selectedImageUiState: SelectedImageUiState,
    paymentUiState: PaymentUiState,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route,
    ) {
        composable(ProfileCreationScreen.PROFILE_CREATION_INTRODUCE.route) {
            ProfileCreationIntroduceScreen(
                navController = navController,
                profileConceptDetail = conceptDetailUiState.conceptDetail,
                hasAlreadyPet = petPhotoSelectionUiState.pets.isNotEmpty(),
            )
        }
        composable(ProfileCreationScreen.PET_PHOTO_SELECT.route) {
            PetPhotoSelectionScreen(
                petPhotoSelectionUiState = petPhotoSelectionUiState,
                navController = navController
            )
        }
        composable(ProfileCreationScreen.PET_NAME.route) {
            PetNameScreen(
                navController = navController,
                petName = petInfoUiState.petName,
                onPetNameChanged = onPetNameChanged
            )
        }
        composable(ProfileCreationScreen.PET_COLOR.route) {
            PetColorScreen(
                selectedPetColor = petInfoUiState.petColor,
                onPetColorSelected = onPetColorSelected,
                onKeepGoingClick = { navController.navigate(ProfileCreationScreen.PHOTO_UPLOAD_INTRODUCE.route) }
            )
        }
        composable(ProfileCreationScreen.PHOTO_UPLOAD_INTRODUCE.route) {
            PhotoUploadIntroduceScreen(
                onPhotoUploadClick = onPhotoUploadClick,
                selectedImageUiState = selectedImageUiState,
            )
        }
        composable(ProfileCreationScreen.PHOTO_UPLOAD_RESULT.route) {
            PhotoUploadResultScreen(
                onPetUploadClick = onPetUploadClick,
                selectedImageUiState = selectedImageUiState,
                onPhotoUploadClick = onPhotoUploadClick,
            )
        }
        composable(ProfileCreationScreen.PAYMENT.route) {
            PaymentScreen(paymentUiState = paymentUiState)
        }
        composable(ProfileCreationScreen.PAYMENT_RESULT.route) {
            PaymentResultScreen(
                petType = conceptDetailUiState.profileConcept?.petType ?: PetType.DOG,
            )
        }
    }
}

enum class ProfileCreationScreen(
    val route: String,
) {
    PROFILE_CREATION_INTRODUCE("profileCreationIntroduce"),
    PET_PHOTO_SELECT("petPhotoSelect"),
    PET_NAME("petName"),
    PET_COLOR("petColor/{petType}"),
    PHOTO_UPLOAD_INTRODUCE("photoUploadIntroduce"),
    PHOTO_UPLOAD_RESULT("photoUploadResult"),
    PAYMENT("payment"),
    PAYMENT_RESULT("paymentResult"),
}
