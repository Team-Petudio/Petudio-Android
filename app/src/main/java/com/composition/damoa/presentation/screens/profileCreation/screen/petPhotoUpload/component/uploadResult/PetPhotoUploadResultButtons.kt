package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadResult

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.presentation.common.components.KeepGoingButton
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.PhotoUploadButton


@Composable
fun PetPhotoUploadResultButtons(
    onPetPhotosUploadClick: () -> Unit,
    onPetUploadClick: () -> Unit,
    isShowKeepGoingButton: Boolean,
) {
    Column(Modifier.padding(bottom = 20.dp)) {
        Spacer(modifier = Modifier.weight(1F))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PhotoUploadButton(modifier = Modifier.weight(1F), onClick = onPetPhotosUploadClick)
            if (isShowKeepGoingButton) KeepGoingButton(modifier = Modifier.weight(1F), onClick = onPetUploadClick)
        }
    }
}