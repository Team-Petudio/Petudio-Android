package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadIntroduce

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.presentation.common.components.SmallTitle
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.PetPhotoItem
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.model.PetPhoto


@Composable
fun PetPhotoUploadExamples(
    modifier: Modifier = Modifier,
    photoType: PetPhoto.PhotoType,
    @StringRes titleRes: Int,
    petPhotos: List<PetPhoto>,
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
            SmallTitle(titleRes = titleRes)
        }
        items(petPhotos) { photo ->
            PetPhotoItem(petPhoto = photo, photoType = photoType)
        }
    }
}