package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.component

import androidx.compose.runtime.Composable
import com.composition.damoa.data.model.Pet


@Composable
fun PetSelectionContent(
    pets: List<Pet>,
    selectedPetId: Long? = null,
    onPetSelected: (petId: Long) -> Unit,
    onNewPhotoUploadClick: () -> Unit,
) {
    SelectablePetList(
        pets = pets,
        selectedPetId = selectedPetId,
        onPetSelected = onPetSelected,
        onNewPhotoUploadClick = onNewPhotoUploadClick,
    )
}