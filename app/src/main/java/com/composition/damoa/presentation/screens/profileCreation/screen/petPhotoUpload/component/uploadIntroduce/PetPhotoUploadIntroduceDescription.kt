package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadIntroduce

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.SmallTitle

@Composable
fun PetPhotoUploadIntroduceDescription(
    modifier: Modifier = Modifier,
) {
    SmallTitle(
        modifier = modifier,
        titleRes = R.string.pet_photo_upload_desc,
    )
}