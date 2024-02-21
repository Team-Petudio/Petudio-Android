package com.composition.damoa.presentation.screens.profileCreation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.screens.profileCreation.screen.payment.PaymentScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.paymentResult.PaymentResultScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petColor.PetColorScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petName.PetNameScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.PetPhotoSelectionScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.photoUploadIntroduce.PhotoUploadIntroduceScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.photoUploadResult.PhotoUploadResultScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.profileCreationIntroduce.ProfileCreationIntroduceScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.profileCreationIntroduce.state.ConceptDetailUiState
import com.composition.damoa.presentation.screens.profileCreation.screen.payment.state.PaymentUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetInfoUiState
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.state.PetPhotoSelectionUiState
import com.composition.damoa.presentation.screens.profileCreation.screen.photoUploadResult.state.PhotoUploadResultUiState


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

@Composable
fun ProfileCreationNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: ProfileCreationScreen = ProfileCreationScreen.PROFILE_CREATION_INTRODUCE,
    conceptDetailUiState: ConceptDetailUiState,
    petPhotoSelectionUiState: PetPhotoSelectionUiState,
    petInfoUiState: PetInfoUiState,
    onPetNameChanged: (String) -> Unit,
    onPetColorSelected: (PetColor) -> Unit,
    onPetUploadClick: () -> Unit,
    onPhotoUploadClick: () -> Unit,
    photoUploadResultUiState: PhotoUploadResultUiState,
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
                photoUploadResultUiState = photoUploadResultUiState,
            )
        }
        composable(ProfileCreationScreen.PHOTO_UPLOAD_RESULT.route) {
            PhotoUploadResultScreen(
                onPetUploadClick = onPetUploadClick,
                photoUploadResultUiState = photoUploadResultUiState,
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