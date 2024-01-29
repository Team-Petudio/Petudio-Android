package com.composition.damoa.presentation.screens.profileCreation.state

import com.composition.damoa.presentation.common.base.BaseUiState
import com.composition.damoa.presentation.screens.profileCreation.Pet

data class PetPhotoUiState(
    override val state: State = State.NONE,
    val pets: List<Pet> = emptyList(),
) : BaseUiState()
