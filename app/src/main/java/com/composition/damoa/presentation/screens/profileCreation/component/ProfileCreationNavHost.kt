package com.composition.damoa.presentation.screens.profileCreation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.screens.profileCreation.screen.creationIntroduce.ProfileCreationIntroduceScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.creationIntroduce.state.ProfileCreationIntroduceUiState
import com.composition.damoa.presentation.screens.profileCreation.screen.payment.PaymentResultScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.payment.PaymentScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.payment.state.PaymentUiState
import com.composition.damoa.presentation.screens.profileCreation.screen.petColor.PetColorScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petName.PetNameScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.PetSelectionScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.state.PetSelectionUiState
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.PetPhotoUploadResultScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.PhotoUploadIntroduceScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.state.PhotoUploadUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetInfoUiState


enum class ProfileCreationScreen(
    val route: String,
) {
    PROFILE_CREATION_INTRODUCE("profileCreationIntroduce"),
    PET_SELECT("petSelect"),
    PET_NAME("petName"),
    PET_COLOR("petColor/{petType}"),
    PHOTO_UPLOAD_INTRODUCE("photoUploadIntroduce"),
    PHOTO_UPLOAD_RESULT("photoUploadResult"),
    PAYMENT("payment"),
    PAYMENT_RESULT("paymentResult"),
}

@Composable
fun ProfileCreationNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: ProfileCreationScreen = ProfileCreationScreen.PROFILE_CREATION_INTRODUCE,
    profileCreationIntroduceUiState: ProfileCreationIntroduceUiState,
    petSelectionUiState: PetSelectionUiState,
    petInfoUiState: PetInfoUiState,
    onPhotoUploadClick: () -> Unit,
    photoUploadUiState: PhotoUploadUiState,
    paymentUiState: PaymentUiState,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route,
    ) {
        composable(ProfileCreationScreen.PROFILE_CREATION_INTRODUCE.route) {
            ProfileCreationIntroduceScreen(
                profileConceptDetail = profileCreationIntroduceUiState.conceptDetail,
                onKeepGoingClick = {
                    val hasPet = petSelectionUiState.pets.isNotEmpty()
                    if (hasPet) {
                        navController.navigate(ProfileCreationScreen.PET_SELECT.route)
                    } else {
                        navController.navigate(ProfileCreationScreen.PET_NAME.route)
                    }
                },
            )
        }
        composable(ProfileCreationScreen.PET_SELECT.route) {
            PetSelectionScreen(
                petSelectionUiState = petSelectionUiState,
                onNewPhotoUploadClick = { navController.navigate(ProfileCreationScreen.PET_NAME.route) },
                onKeepGoingClick = { navController.navigate(ProfileCreationScreen.PAYMENT.route) },
            )
        }
        composable(ProfileCreationScreen.PET_NAME.route) {
            PetNameScreen(
                petInfoUiState = petInfoUiState,
                onKeepGoingClick = { navController.navigate(ProfileCreationScreen.PET_COLOR.route) },
            )
        }
        composable(ProfileCreationScreen.PET_COLOR.route) {
            PetColorScreen(
                petInfoUiState = petInfoUiState,
                onKeepGoingClick = { navController.navigate(ProfileCreationScreen.PHOTO_UPLOAD_INTRODUCE.route) }
            )
        }
        composable(ProfileCreationScreen.PHOTO_UPLOAD_INTRODUCE.route) {
            PhotoUploadIntroduceScreen(
                photoUploadUiState = photoUploadUiState,
                onPhotoUploadClick = onPhotoUploadClick,
            )
        }
        composable(ProfileCreationScreen.PHOTO_UPLOAD_RESULT.route) {
            PetPhotoUploadResultScreen(
                photoUploadUiState = photoUploadUiState,
                petInfoUiState = petInfoUiState,
                onPetPhotosUploadClick = onPhotoUploadClick,
            )
        }
        composable(ProfileCreationScreen.PAYMENT.route) {
            PaymentScreen(paymentUiState = paymentUiState)
        }
        composable(ProfileCreationScreen.PAYMENT_RESULT.route) {
            PaymentResultScreen(
                petType = profileCreationIntroduceUiState.profileConcept?.petType ?: PetType.DOG,
            )
        }
    }
}