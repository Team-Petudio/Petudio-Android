package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.SmallDescription
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.model.PetPhoto
import com.composition.damoa.presentation.ui.theme.AlertIconColor
import com.composition.damoa.presentation.ui.theme.GoodIconColor
import com.composition.damoa.presentation.ui.theme.Gray40
import java.io.File


@Composable
fun PetPhotoItem(
    modifier: Modifier = Modifier,
    petPhoto: PetPhoto,
    photoType: PetPhoto.PhotoType,
) {
    Column(modifier) {
        PetPhotoItem(
            petPhoto = petPhoto.imageRes,
            photoType = photoType
        )
        SmallDescription(
            modifier = Modifier.padding(top = 8.dp),
            descriptionRes = petPhoto.descRes,
        )
    }
}

@Composable
private fun PetPhotoItem(
    modifier: Modifier = Modifier,
    @DrawableRes petPhoto: Int,
    photoType: PetPhoto.PhotoType,
    onDelete: () -> Unit = {},
) {
    Box(modifier = modifier.padding(6.dp)) {
        PetPhotoItem(petPhoto = petPhoto)
        PetPhotoTypeIcon(
            modifier = Modifier
                .padding(6.dp)
                .size(28.dp),
            petPhotoType = photoType,
        )
        if (photoType == PetPhoto.PhotoType.GOOD_EXAMPLE) {
            DeleteButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp),
                onClick = onDelete,
            )
        }
    }
}

@Composable
fun PetPhotoItem(
    modifier: Modifier = Modifier,
    petPhoto: File,
    photoType: PetPhoto.PhotoType,
    onDelete: () -> Unit = {},
) {
    Box(modifier = modifier.padding(6.dp)) {
        PetPhotoItem(petPhoto = petPhoto)
        PetPhotoTypeIcon(
            modifier = Modifier
                .padding(6.dp)
                .size(28.dp),
            petPhotoType = photoType,
        )
        if (photoType == PetPhoto.PhotoType.GOOD_EXAMPLE) {
            DeleteButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp),
                onClick = onDelete,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun PetPhotoItem(
    modifier: Modifier = Modifier,
    petPhoto: Any,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp,
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(1F),
    ) {
        GlideImage(
            model = petPhoto,
            contentDescription = null,
            transition = CrossFade,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun PetPhotoTypeIcon(
    modifier: Modifier = Modifier,
    petPhotoType: PetPhoto.PhotoType,
) {
    when (petPhotoType) {
        PetPhoto.PhotoType.GOOD_EXAMPLE -> Icon(
            modifier = modifier,
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = GoodIconColor,
        )

        PetPhoto.PhotoType.BAD_EXAMPLE -> Icon(
            modifier = modifier,
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            tint = AlertIconColor,
        )
    }
}

@Composable
private fun DeleteButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Surface(
        color = Gray40,
        shape = RoundedCornerShape(8.dp),
        modifier =
        modifier
            .size(24.dp)
            .clickable { onClick() },
    ) {
        Icon(
            modifier = Modifier.padding(4.dp),
            imageVector = Icons.Filled.Close,
            contentDescription = null,
            tint = Color.White,
        )
    }
}
