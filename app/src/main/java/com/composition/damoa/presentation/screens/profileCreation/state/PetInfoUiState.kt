package com.composition.damoa.presentation.screens.profileCreation.state

import com.composition.damoa.data.model.PetColor

data class PetInfoUiState(
    val petName: String = "",
    val petColor: PetColor? = null,
    val petPhotoUrls: List<String> = emptyList(),
)
