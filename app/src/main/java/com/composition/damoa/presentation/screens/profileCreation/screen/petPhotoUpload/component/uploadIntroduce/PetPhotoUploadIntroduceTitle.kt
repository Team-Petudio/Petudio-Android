package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadIntroduce

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigTitle


@Composable
fun PetPhotoUploadIntroduceTitle(
    modifier: Modifier = Modifier,
) {
    BigTitle(
        modifier = modifier,
        titleRes = R.string.pet_photo_upload_introduce_title,
    )
}