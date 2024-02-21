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
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.component.PetSelectionContent
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.state.PetSelectionUiState

@Composable
fun PetSelectionScreen(
    modifier: Modifier = Modifier,
    petSelectionUiState: PetSelectionUiState,
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
        PetSelectionContent(
            pets = petSelectionUiState.pets,
            selectedPetId = petSelectionUiState.selectedPetId,
            onPetSelected = { petId -> petSelectionUiState.onPetSelected(petId) },
            onNewPhotoUploadClick = onNewPhotoUploadClick,
        )
        KeepGoingButton(
            enabled = petSelectionUiState.selectedPetId != null,
            onClick = onKeepGoingClick,
        )
    }
}
