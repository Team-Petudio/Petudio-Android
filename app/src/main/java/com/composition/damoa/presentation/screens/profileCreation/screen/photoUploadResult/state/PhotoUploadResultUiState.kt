package com.composition.damoa.presentation.screens.profileCreation.screen.photoUploadResult.state

import com.composition.damoa.presentation.common.base.BaseUiState
import java.io.File

data class PhotoUploadResultUiState(
    override val state: State = State.NONE,
    val selectedImageFiles: List<File> = emptyList(),
    val badImageFiles: List<File> = emptyList(),
    val onUnselectImage: (File) -> Unit,
) : BaseUiState() {
    val canMoreSelectPhotoSize = 12 - selectedImageFiles.size

    fun isValidPetPhotoSize() = selectedImageFiles.size in 10..12
}