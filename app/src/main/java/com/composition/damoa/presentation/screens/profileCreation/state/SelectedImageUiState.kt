package com.composition.damoa.presentation.screens.profileCreation.state

import com.composition.damoa.presentation.common.base.BaseUiState
import java.io.File

data class SelectedImageUiState(
    override val state: State = State.NONE,
    val selectedImageFiles: List<File> = emptyList(),
    val badImageFiles: List<File> = emptyList(),
    val onUnselectImage: (File) -> Unit,
) : BaseUiState() {
    val canMoreSelectPhotoSize = 12 - selectedImageFiles.size

    fun isValidPetPhotoSize() = selectedImageFiles.size in 10..12
}