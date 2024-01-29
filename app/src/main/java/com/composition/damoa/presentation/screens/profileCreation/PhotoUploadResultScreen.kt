package com.composition.damoa.presentation.screens.profileCreation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composition.damoa.R
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.KeepGoingButton
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.SmallDescription
import com.composition.damoa.presentation.common.components.TinyTitle
import com.composition.damoa.presentation.common.utils.badDogPhotoExamples
import com.composition.damoa.presentation.common.utils.goodDogPhotoExamples
import com.composition.damoa.presentation.screens.profileCreation.component.PetPhoto
import com.composition.damoa.presentation.screens.profileCreation.component.PhotoUploadButton

@Composable
fun PhotoUploadResultScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    petType: PetType = PetType.DOG,
    isShowKeepGoingButton: Boolean,
    onPetAddClick: () -> Unit,
) {
    Surface(
        color = Color.White,
        modifier =
        modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        PhotoUploadIntroduceContent(
            petType = petType,
            badPetPhotos = badDogPhotoExamples(),
            goodPetPhotos = goodDogPhotoExamples(),
        )
        Column(Modifier.padding(bottom = 20.dp)) {
            Spacer(modifier = Modifier.weight(1F))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PhotoUploadButton(modifier = Modifier.weight(1F)) {
                    // 갤러리
                }
                if (isShowKeepGoingButton) {
                    KeepGoingButton(modifier = Modifier.weight(1F), onClick = onPetAddClick)
                }
            }
        }
    }
}

@Composable
private fun PhotoUploadIntroduceContent(
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
            BigTitle(
                titleRes = R.string.pet_photo_upload_introduce_title,
                modifier = Modifier.padding(top = 20.dp),
            )
        }
        item {
            MediumDescription(
                descriptionRes = R.string.pet_photo_upload_result_desc,
                modifier = Modifier.padding(top = 12.dp),
            )
        }
        item {
            PetPhotoUploadResult(
                modifier = Modifier.padding(top = 32.dp),
                petPhotos = badPetPhotos,
                titleRes = R.string.pet_photo_upload_bad_result_title,
                photoType = PetPhoto.PhotoType.BAD_EXAMPLE,
            )
        }
        item {
            PetPhotoUploadResult(
                modifier = Modifier.padding(top = 32.dp),
                petPhotos = goodPetPhotos,
                titleRes = R.string.pet_photo_upload_good_result_title,
                photoType = PetPhoto.PhotoType.GOOD_EXAMPLE,
            )
        }
    }
}

@Composable
private fun PetPhotoUploadResult(
    modifier: Modifier = Modifier,
    petPhotos: List<PetPhoto>,
    @StringRes titleRes: Int,
    photoType: PetPhoto.PhotoType,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 700.dp)
            .wrapContentHeight(),
        userScrollEnabled = false,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(span = { GridItemSpan(2) }) {
            TinyTitle(titleRes = titleRes)
        }
        items(petPhotos) { photo ->
            PetPhotoList(petPhoto = photo, photoType = photoType, onDelete = {})
        }
    }
}

@Composable
private fun PetPhotoList(
    modifier: Modifier = Modifier,
    petPhoto: PetPhoto,
    photoType: PetPhoto.PhotoType,
    onDelete: () -> Unit,
) {
    Column(modifier) {
        PetPhoto(
            petPhoto = petPhoto,
            photoType = photoType,
            isDeletable = true,
            onDelete = onDelete,
        )
        SmallDescription(
            descriptionRes = petPhoto.descRes,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}
