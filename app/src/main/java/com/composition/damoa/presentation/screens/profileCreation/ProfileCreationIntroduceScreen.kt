package com.composition.damoa.presentation.screens.profileCreation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.KeepGoingButton
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.SmallTitle
import com.composition.damoa.presentation.common.utils.profileExamplePhotoUrls

@Composable
fun ProfileCreationIntroduceScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Surface(
        color = Color.White,
        modifier =
            modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
    ) {
        ProfileCreationIntroduceContent(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp),
        )
        KeepGoingButton(onClick = {
            navController.navigate(ProfileCreationScreen.PET_PHOTO_SELECT.route)
        })
    }
}

@Composable
private fun ProfileCreationIntroduceContent(modifier: Modifier = Modifier) {
    Column(modifier) {
        ProfileCreationIntroduceTitle()
        ProfileCreationGoodExample()
        ProfileCreationBadExample(modifier = Modifier.padding(top = 32.dp))
    }
}

@Composable
private fun ProfileCreationIntroduceTitle() {
    BigTitle(
        titleRes = R.string.profile_create_introduce_title,
        modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
    )
}

@Composable
private fun ProfileCreationGoodExample(modifier: Modifier = Modifier) {
    ProfileCreationIntroduce(
        modifier = modifier,
        titleRes = R.string.profile_create_good_example_intro_title,
        descriptionRes = R.string.profile_create_good_example_intro_desc,
        profileExamplePhotoUrls = profileExamplePhotoUrls(),
    )
}

@Composable
private fun ProfileCreationBadExample(modifier: Modifier = Modifier) {
    ProfileCreationIntroduce(
        modifier = modifier,
        isShowAlertIcon = true,
        titleRes = R.string.profile_create_bad_example_intro_title,
        descriptionRes = R.string.profile_create_bad_example_intro_desc,
        profileExamplePhotoUrls = profileExamplePhotoUrls(),
    )
}

@Composable
private fun ProfileCreationIntroduce(
    modifier: Modifier = Modifier,
    isShowAlertIcon: Boolean = false,
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int,
    profileExamplePhotoUrls: List<String>,
) {
    Column(modifier) {
        ProfileCreationIntroduceTitle(titleRes = titleRes, isShowAlertIcon = isShowAlertIcon)
        ProfileCreationDescription(descriptionRes = descriptionRes)
        ProfileExamplePhotos(profileExamplePhotoUrls)
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
private fun ProfileExamplePhotos(photoUrls: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ProfileExamplePhoto(photoUrls[0], modifier = Modifier.weight(1F))
            ProfileExamplePhoto(photoUrls[1], modifier = Modifier.weight(1F))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ProfileExamplePhoto(photoUrls[2], modifier = Modifier.weight(1F))
            ProfileExamplePhoto(photoUrls[3], modifier = Modifier.weight(1F))
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ProfileExamplePhoto(
    photoUrl: String,
    modifier: Modifier = Modifier,
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
        )
    }
}

@Preview
@Composable
private fun ProfileCreationPreview() {
    ProfileCreationIntroduceScreen(
        navController = rememberNavController(),
    )
}
