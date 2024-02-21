package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composition.damoa.presentation.common.base.BaseUiState
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.PhotoUploadLoadingScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadResult.PetPhotoUploadResultButtons
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadResult.PetPhotoUploadResultContent
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.state.PhotoUploadUiState
import com.composition.damoa.presentation.screens.profileCreation.state.PetInfoUiState


@Composable
fun PetPhotoUploadResultScreen(
    modifier: Modifier = Modifier,
    photoUploadUiState: PhotoUploadUiState,
    petInfoUiState: PetInfoUiState,
    onPetPhotosUploadClick: () -> Unit,
) {
    Surface(
        color = Color.White,
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        PetPhotoUploadResultContent(
            badPetPhotos = photoUploadUiState.badImageFiles,
            goodPetPhotos = photoUploadUiState.selectedImageFiles,
            onUnselectImage = photoUploadUiState.onUnselectImage,
        )
        PetPhotoUploadResultButtons(
            onPetPhotosUploadClick = onPetPhotosUploadClick,
            onPetUploadClick = petInfoUiState.onPetUploadClick,
            isShowKeepGoingButton = photoUploadUiState.isValidPetPhotoSize(),
        )
        if (photoUploadUiState.state == BaseUiState.State.LOADING) PhotoUploadLoadingScreen()
    }
}
