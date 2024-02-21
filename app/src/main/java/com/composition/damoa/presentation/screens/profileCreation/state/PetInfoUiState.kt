package com.composition.damoa.presentation.screens.profileCreation.state

import com.composition.damoa.data.model.PetColor
import com.composition.damoa.presentation.common.base.BaseUiState

data class PetInfoUiState(
    override val state: State = State.NONE,
    val petName: String = "",
    val petColor: PetColor? = null,
    val onPetColorSelected: (PetColor) -> Unit,
    val uploadedPetPhotoUrls: List<String> = emptyList(),
) : BaseUiState()
