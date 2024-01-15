package com.composition.damoa.presentation.screens.profileCreation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.R
import com.composition.damoa.presentation.common.utils.badDogPhotoExamples
import com.composition.damoa.presentation.common.utils.goodDogPhotoExamples
import com.composition.damoa.presentation.screens.profileCreation.PetPhoto
import com.composition.damoa.presentation.ui.theme.AlertIconColor
import com.composition.damoa.presentation.ui.theme.GoodIconColor
import com.composition.damoa.presentation.ui.theme.Gray40

@Composable
fun PetPhoto(
    modifier: Modifier = Modifier,
    petPhoto: PetPhoto,
    photoType: PetPhoto.PhotoType,
    isDeletable: Boolean = false,
    onDelete: () -> Unit = {},
) {
    Box(modifier.padding(6.dp)) {
        PetPhoto(petPhoto = petPhoto)
        ExampleIcon(
            modifier = Modifier.size(28.dp),
            photoType = photoType,
        )
        if (isDeletable) {
            DeleteButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = onDelete,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun PetPhoto(
    modifier: Modifier = Modifier,
    petPhoto: PetPhoto,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp,
        modifier =
            modifier
                .fillMaxHeight()
                .aspectRatio(1F),
    ) {
        GlideImage(
            model = petPhoto.imageRes,
            contentDescription = null,
            transition = CrossFade,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun ExampleIcon(
    modifier: Modifier = Modifier,
    photoType: PetPhoto.PhotoType,
) {
    when (photoType) {
        PetPhoto.PhotoType.GOOD_EXAMPLE ->
            Icon(
                modifier = modifier,
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = GoodIconColor,
            )

        PetPhoto.PhotoType.BAD_EXAMPLE ->
            Icon(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                tint = AlertIconColor,
            )

        PetPhoto.PhotoType.NONE -> {}
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

@Preview
@Composable
private fun JustPetPhotoPreview() {
    PetPhoto(
        petPhoto = goodDogPhotoExamples().first(),
        photoType = PetPhoto.PhotoType.NONE,
    )
}

@Preview
@Composable
private fun BadPetPhotoPreview() {
    PetPhoto(
        petPhoto = badDogPhotoExamples().first(),
        photoType = PetPhoto.PhotoType.BAD_EXAMPLE,
    )
}

@Preview
@Composable
private fun DeletablePetPhotoPreview() {
    PetPhoto(
        petPhoto = goodDogPhotoExamples().first(),
        photoType = PetPhoto.PhotoType.GOOD_EXAMPLE,
        isDeletable = true,
    )
}
