package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.state

import com.composition.damoa.R
import com.composition.damoa.presentation.common.base.BaseUiState
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.model.PetPhoto
import java.io.File

data class PhotoUploadUiState(
    override val state: State = State.NONE,
    val selectedImageFiles: List<File> = emptyList(),
    val badImageFiles: List<File> = emptyList(),
    val onUnselectImage: (File) -> Unit,
) : BaseUiState() {
    val canMoreSelectPhotoSize = 12 - selectedImageFiles.size
    val goodDogPhotoExamples: List<PetPhoto> = listOf(
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_good_example1,
            descRes = R.string.dog_photo_upload_good_example_desc1,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_good_example2,
            descRes = R.string.dog_photo_upload_good_example_desc2,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_good_example3,
            descRes = R.string.dog_photo_upload_good_example_desc3,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_good_example4,
            descRes = R.string.dog_photo_upload_good_example_desc4,
        ),
    )

    val badDogPhotoExamples: List<PetPhoto> = listOf(
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_bad_example1,
            descRes = R.string.dog_photo_upload_bad_example_desc1,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_bad_example2,
            descRes = R.string.dog_photo_upload_bad_example_desc2,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_bad_example3,
            descRes = R.string.dog_photo_upload_bad_example_desc3,
        ),
        PetPhoto(
            imageRes = R.drawable.img_photo_upload_bad_example4,
            descRes = R.string.dog_photo_upload_bad_example_desc4,
        ),
    )

    fun isValidPetPhotoSize() = selectedImageFiles.size in 10..12
}