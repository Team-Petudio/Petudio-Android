package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadResult

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.MediumDescription


@Composable
fun PetPhotoUploadResultDescription(
    modifier: Modifier = Modifier,
) {
    MediumDescription(
        modifier = modifier,
        descriptionRes = R.string.pet_photo_upload_result_desc,
    )
}