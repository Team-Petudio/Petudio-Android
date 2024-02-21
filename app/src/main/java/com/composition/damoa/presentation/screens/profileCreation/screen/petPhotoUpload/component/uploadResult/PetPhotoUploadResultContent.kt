package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadResult

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.model.PetPhoto
import java.io.File


@Composable
fun PetPhotoUploadResultContent(
    modifier: Modifier = Modifier,
    badPetPhotos: List<File>,
    goodPetPhotos: List<File>,
    onUnselectImage: (File) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 100.dp),
    ) {
        item {
            Column {
                PetPhotoUploadResultTitle(modifier = Modifier.padding(top = 20.dp))
                PetPhotoUploadResultDescription(modifier = Modifier.padding(top = 12.dp))
            }
        }
        item {
            PetPhotoUploadResultList(
                modifier = Modifier.padding(top = 32.dp),
                petPhotos = badPetPhotos,
                titleRes = R.string.pet_photo_upload_bad_result_title,
                photoType = PetPhoto.PhotoType.BAD_EXAMPLE,
            )
        }
        item {
            PetPhotoUploadResultList(
                modifier = Modifier.padding(top = 32.dp),
                petPhotos = goodPetPhotos,
                titleRes = R.string.pet_photo_upload_good_result_title,
                photoType = PetPhoto.PhotoType.GOOD_EXAMPLE,
                onUnselectImage = onUnselectImage,
            )
        }
    }
}
