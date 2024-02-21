package com.composition.damoa.presentation.screens.profileCreation.screen.profileCreationIntroduce

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.composition.damoa.R
import com.composition.damoa.data.model.ProfileConceptDetail
import com.composition.damoa.presentation.common.components.KeepGoingButton
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.common.components.SmallTitle
import com.composition.damoa.presentation.screens.profileCreation.component.ProfileCreationScreen


@Composable
fun ProfileCreationIntroduceScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    profileConceptDetail: ProfileConceptDetail,
    hasAlreadyPet: Boolean,
) {
    Surface(
        color = Color.White,
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        ProfileCreationIntroduceContent(profileConceptDetail = profileConceptDetail)
        KeepGoingButton(onClick = {
            if (hasAlreadyPet) {
                navController.navigate(ProfileCreationScreen.PET_PHOTO_SELECT.route)
            } else {
                navController.navigate(ProfileCreationScreen.PET_NAME.route)
            }
        })
    }
}

@Composable
private fun ProfileCreationIntroduceContent(
    modifier: Modifier = Modifier,
    profileConceptDetail: ProfileConceptDetail,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 100.dp),
    ) {
        item {
            ProfileCreationIntroduceTitle()
        }
        item {
            ProfileCreationGoodHeader()
        }
        item {
            ProfileExamplePhotos(photoUrls = profileConceptDetail.successImageUrls)
        }
        item {
            ProfileCreationBadHeader(modifier = Modifier.padding(top = 32.dp))
        }
        item {
            ProfileExamplePhotos(photoUrls = profileConceptDetail.failImageUrls)
        }
    }
}

@Composable
private fun ProfileCreationIntroduceTitle() {
    MediumTitle(
        titleRes = R.string.profile_create_introduce_title,
        modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
    )
}

@Composable
private fun ProfileCreationGoodHeader(
    modifier: Modifier = Modifier,
) {
    ProfileCreationIntroduce(
        modifier = modifier,
        titleRes = R.string.profile_create_good_example_intro_title,
        descriptionRes = R.string.profile_create_good_example_intro_desc,
    )
}

@Composable
private fun ProfileCreationBadHeader(
    modifier: Modifier = Modifier,
) {
    ProfileCreationIntroduce(
        modifier = modifier,
        isShowAlertIcon = true,
        titleRes = R.string.profile_create_bad_example_intro_title,
        descriptionRes = R.string.profile_create_bad_example_intro_desc,
    )
}

@Composable
private fun ProfileCreationIntroduce(
    modifier: Modifier = Modifier,
    isShowAlertIcon: Boolean = false,
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int,
) {
    Column(modifier) {
        ProfileCreationIntroduceTitle(titleRes = titleRes, isShowAlertIcon = isShowAlertIcon)
        ProfileCreationDescription(descriptionRes = descriptionRes)
    }
}

@Composable
private fun ProfileCreationIntroduceTitle(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    isShowAlertIcon: Boolean = false,
) {
    SmallTitle(
        modifier = modifier.padding(bottom = 12.dp),
        titleRes = titleRes,
        isShowAlertIcon = isShowAlertIcon,
    )
}

@Composable
private fun ProfileCreationDescription(
    @StringRes descriptionRes: Int,
    modifier: Modifier = Modifier,
) {
    MediumDescription(
        modifier = modifier.padding(bottom = 16.dp),
        descriptionRes = descriptionRes,
    )
}

@Composable
private fun ProfileExamplePhotos(
    modifier: Modifier = Modifier,
    photoUrls: List<String>,
) {
    LazyVerticalGrid(
        modifier = modifier
            .background(Color.White)
            .heightIn(max = 1000.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 20.dp),
    ) {
        items(photoUrls) { photoUrl ->
            ProfileExamplePhoto(photoUrl = photoUrl)
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ProfileExamplePhoto(
    modifier: Modifier = Modifier,
    photoUrl: String,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp,
        modifier = modifier.aspectRatio(1F),
    ) {
        GlideImage(
            model = photoUrl,
            contentDescription = null,
            transition = CrossFade,
            contentScale = ContentScale.Crop,
            failure = placeholder(R.drawable.img_placeholder),
            loading = placeholder(R.drawable.img_placeholder),
        )
    }
}