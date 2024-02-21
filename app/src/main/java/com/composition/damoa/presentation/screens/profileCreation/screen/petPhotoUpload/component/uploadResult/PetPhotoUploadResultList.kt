package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component.uploadResult

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
import com.composition.damoa.presentation.common.components.TinyTitle
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.model.PetPhoto
import java.io.File


private const val COLUMN_SPAN = 2

@Composable
fun PetPhotoUploadResultList(
    modifier: Modifier = Modifier,
    petPhotos: List<File>,
    @StringRes titleRes: Int,
    photoType: PetPhoto.PhotoType,
    onUnselectImage: (File) -> Unit = {},
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 700.dp)
            .wrapContentHeight(),
        userScrollEnabled = false,
        columns = GridCells.Fixed(COLUMN_SPAN),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(span = { GridItemSpan(COLUMN_SPAN) }) {
            TinyTitle(titleRes = titleRes)
        }
        items(petPhotos) { photo ->
            PetPhotoUploadResultItem(
                petPhoto = photo,
                photoType = photoType,
                onUnselectImage = { onUnselectImage(photo) }
            )
        }
    }
}
