package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadResult

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.PetPhotoItem
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.model.PetPhoto
import java.io.File


@Composable
fun PetPhotoUploadResultItem(
    modifier: Modifier = Modifier,
    petPhoto: File,
    photoType: PetPhoto.PhotoType,
    onUnselectImage: () -> Unit,
) {
    Column(modifier) {
        PetPhotoItem(
            petPhoto = petPhoto,
            photoType = photoType,
            onDelete = onUnselectImage,
        )
    }
}