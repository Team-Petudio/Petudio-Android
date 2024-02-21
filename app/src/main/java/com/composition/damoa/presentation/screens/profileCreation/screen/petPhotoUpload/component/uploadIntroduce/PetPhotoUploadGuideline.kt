package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadIntroduce

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.common.components.MediumDescription


@Composable
fun PetPhotoUploadGuideline(
    modifier: Modifier = Modifier,
    petType: PetType,
) {
    val photoUploadDescriptionRes = when (petType) {
        PetType.DOG -> R.string.dog_photo_upload_guideline_desc
        PetType.CAT -> R.string.cat_photo_upload_guideline_desc
    }

    MediumDescription(
        modifier = modifier,
        descriptionRes = photoUploadDescriptionRes,
    )
}