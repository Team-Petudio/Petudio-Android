package com.composition.damoa.presentation.screens.profileCreation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.SmallDescription
import com.composition.damoa.presentation.common.components.SmallTitle
import com.composition.damoa.presentation.common.utils.badDogPhotoExamples
import com.composition.damoa.presentation.common.utils.goodDogPhotoExamples
import com.composition.damoa.presentation.ui.theme.AlertIconColor
import com.composition.damoa.presentation.ui.theme.GoodIconColor
import com.composition.damoa.presentation.ui.theme.PrimaryColors

data class PetPhoto(
    @DrawableRes val imageRes: Int,
    @StringRes val descRes: Int,
)

@Composable
fun PhotoUploadIntroduceScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    petType: PetType = PetType.DOG,
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
        PhotoUploadButton(onClick = {
            // 갤러리
        })
    }
}

@Composable
private fun PhotoUploadIntroduceContent(
    modifier: Modifier = Modifier,
    petType: PetType,
    badPetPhotos: List<PetPhoto>,
    goodPetPhotos: List<PetPhoto>,
) {
    val photoUploadDescRes =
        when (petType) {
            PetType.DOG -> R.string.dog_photo_upload_introduce_desc
            PetType.CAT -> R.string.cat_photo_upload_introduce_desc
        }
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
                descriptionRes = photoUploadDescRes,
                modifier = Modifier.padding(top = 12.dp),
            )
        }
        item {
            PetPhotoUploadExamples(
                modifier = Modifier.padding(top = 32.dp),
                petPhotos = badPetPhotos,
                titleRes = R.string.pet_photo_upload_bad_example_title,
                isGoodExample = false,
            )
        }
        item {
            PetPhotoUploadExamples(
                modifier = Modifier.padding(top = 32.dp),
                petPhotos = goodPetPhotos,
                titleRes = R.string.pet_photo_upload_good_example_title,
                isGoodExample = true,
            )
        }
    }
}

@Composable
private fun PetPhotoUploadExamples(
    modifier: Modifier = Modifier,
    petPhotos: List<PetPhoto>,
    @StringRes titleRes: Int,
    isGoodExample: Boolean,
) {
    LazyVerticalGrid(
        modifier =
            modifier
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
            PetPhotoUploadExample(petPhoto = photo, isGoodExample = isGoodExample)
        }
    }
}

@Composable
private fun PetPhotoUploadExample(
    modifier: Modifier = Modifier,
    petPhoto: PetPhoto,
    isGoodExample: Boolean,
) {
    Column(modifier) {
        Box {
            ExamplePhoto(petPhoto = petPhoto)
            ExampleIcon(
                modifier = Modifier.padding(6.dp).size(28.dp),
                isGoodExample = isGoodExample,
            )
        }
        SmallDescription(
            descriptionRes = petPhoto.descRes,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ExamplePhoto(
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
    isGoodExample: Boolean,
) {
    if (isGoodExample) {
        Icon(
            modifier = modifier,
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = if (isGoodExample) GoodIconColor else AlertIconColor,
        )
    } else {
        Icon(
            modifier = modifier,
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            tint = if (isGoodExample) GoodIconColor else AlertIconColor,
        )
    }
}

@Composable
fun PhotoUploadButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.weight(1F))
        PhotoUploadButton(
            modifier = Modifier.padding(bottom = 20.dp),
            enabled = enabled,
            onClick = onClick,
        )
    }
}

@Composable
private fun PhotoUploadButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    GradientButton(
        modifier =
            modifier
                .fillMaxWidth()
                .aspectRatio(6 / 1F),
        shape = RoundedCornerShape(12.dp),
        text = stringResource(id = R.string.pet_photo_add),
        fontColor = Color.White,
        fontSize = 16.sp,
        enabled = enabled,
        fontWeight = FontWeight.Bold,
        gradient = Brush.horizontalGradient(PrimaryColors),
        onClick = onClick,
    )
}

@Preview
@Composable
private fun PhotoUploadIntroduceScreenPreview() {
    PhotoUploadIntroduceScreen(
        navController = rememberNavController(),
        petType = PetType.DOG,
    )
}
