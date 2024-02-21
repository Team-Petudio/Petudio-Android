package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.state

import com.composition.damoa.data.model.Pet
import com.composition.damoa.presentation.common.base.BaseUiState

data class PetPhotoSelectionUiState(
    override val state: State = State.NONE,
    val pets: List<Pet> = emptyList(),
    val selectedPetId: Long? = null,
    val onPetSelected: (petId: Long) -> Unit,
) : BaseUiState()
