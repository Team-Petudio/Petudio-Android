package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadIntroduce

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.model.PetPhoto


@Composable
fun PetPhotoUploadIntroduceContent(
    modifier: Modifier = Modifier,
    petType: PetType,
    badPetPhotos: List<PetPhoto>,
    goodPetPhotos: List<PetPhoto>,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 100.dp),
    ) {
        item {
            Column {
                PetPhotoUploadIntroduceTitle(modifier = Modifier.padding(top = 20.dp))
                PetPhotoUploadIntroduceDescription(modifier = Modifier.padding(top = 12.dp))
                PetPhotoUploadGuideline(modifier = Modifier.padding(top = 12.dp), petType = petType)
            }
        }
        item {
            PetPhotoUploadExamples(
                modifier = Modifier.padding(top = 32.dp),
                photoType = PetPhoto.PhotoType.BAD_EXAMPLE,
                titleRes = R.string.pet_photo_upload_bad_example_title,
                petPhotos = badPetPhotos,
            )
        }
        item {
            PetPhotoUploadExamples(
                modifier = Modifier.padding(top = 32.dp),
                photoType = PetPhoto.PhotoType.GOOD_EXAMPLE,
                titleRes = R.string.pet_photo_upload_good_example_title,
                petPhotos = goodPetPhotos,
            )
        }
    }
}