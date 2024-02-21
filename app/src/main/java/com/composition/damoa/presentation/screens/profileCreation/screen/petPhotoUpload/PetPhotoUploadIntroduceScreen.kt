package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.common.base.BaseUiState.State
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.PhotoUploadButton
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.PhotoUploadLoadingScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadIntroduce.PetPhotoUploadIntroduceContent
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.state.PhotoUploadUiState


@Composable
fun PhotoUploadIntroduceScreen(
    modifier: Modifier = Modifier,
    photoUploadUiState: PhotoUploadUiState,
    petType: PetType = PetType.DOG,
    onPhotoUploadClick: () -> Unit,
) {
    Surface(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        color = Color.White,
    ) {
        PetPhotoUploadIntroduceContent(
            petType = petType,
            badPetPhotos = photoUploadUiState.badDogPhotoExamples,
            goodPetPhotos = photoUploadUiState.goodDogPhotoExamples,
        )
        PhotoUploadButton(onClick = onPhotoUploadClick)
        if (photoUploadUiState.state == State.LOADING) PhotoUploadLoadingScreen()
    }
}
