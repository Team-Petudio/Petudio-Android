package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composition.damoa.presentation.common.components.KeepGoingButton
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.component.PetPhotoSelectContent
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.state.PetPhotoSelectionUiState

@Composable
fun PetPhotoSelectionScreen(
    modifier: Modifier = Modifier,
    petPhotoSelectionUiState: PetPhotoSelectionUiState,
    onNewPhotoUploadClick: () -> Unit,
    onKeepGoingClick: () -> Unit,
) {
    Surface(
        color = Color.White,
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        PetPhotoSelectContent(
            pets = petPhotoSelectionUiState.pets,
            selectedPetId = petPhotoSelectionUiState.selectedPetId,
            onPetSelected = { petId -> petPhotoSelectionUiState.onPetSelected(petId) },
            onNewPhotoUploadClick = onNewPhotoUploadClick,
        )
        KeepGoingButton(
            enabled = petPhotoSelectionUiState.selectedPetId != null,
            onClick = onKeepGoingClick,
        )
    }
}
