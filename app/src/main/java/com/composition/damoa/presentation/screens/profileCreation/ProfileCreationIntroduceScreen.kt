package com.composition.damoa.presentation.screens.profileCreation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.utils.profileExamplePhotoUrls
import com.composition.damoa.presentation.ui.theme.AlertIconColor
import com.composition.damoa.presentation.ui.theme.Gray60
import com.composition.damoa.presentation.ui.theme.PrimaryColors

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
    Text(
        text = stringResource(id = R.string.profile_create_introduce_title),
        fontSize = 26.sp,
        fontWeight = FontWeight.Black,
        color = Color.Black,
        modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
    )
}

@Composable
private fun ProfileCreationGoodExample(modifier: Modifier = Modifier) {
    ProfileCreationIntroduce(
        modifier = modifier,
        title = stringResource(id = R.string.profile_create_good_example_intro_title),
        description = stringResource(id = R.string.profile_create_good_example_intro_desc),
        profileExamplePhotoUrls = profileExamplePhotoUrls(),
    )
}

@Composable
private fun ProfileCreationBadExample(modifier: Modifier = Modifier) {
    ProfileCreationIntroduce(
        modifier = modifier,
        title = stringResource(id = R.string.profile_create_bad_example_intro_title),
        isShowAlertIcon = true,
        description = stringResource(id = R.string.profile_create_bad_example_intro_desc),
        profileExamplePhotoUrls = profileExamplePhotoUrls(),
    )
}

@Composable
private fun ProfileCreationIntroduce(
    modifier: Modifier = Modifier,
    title: String,
    isShowAlertIcon: Boolean = false,
    description: String,
    profileExamplePhotoUrls: List<String>,
) {
    Column(modifier) {
        ProfileCreationIntroduceTitle(title = title, isShowAlertIcon = isShowAlertIcon)
        ProfileCreationDescription(description)
        ProfileExamplePhotos(profileExamplePhotoUrls)
    }
}

@Composable
private fun ProfileCreationIntroduceTitle(
    modifier: Modifier = Modifier,
    title: String,
    isShowAlertIcon: Boolean = false,
) {
    Row(modifier) {
        if (isShowAlertIcon) {
            Image(
                painter = painterResource(id = R.drawable.alert),
                contentDescription = null,
                colorFilter = ColorFilter.tint(AlertIconColor),
                modifier = Modifier.padding(end = 8.dp),
            )
        }
        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black,
            modifier = modifier.padding(bottom = 12.dp),
        )
    }
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
private fun ProfileCreationDescription(
    description: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = description,
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        color = Gray60,
        modifier = modifier.padding(bottom = 16.dp),
    )
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

@Composable
private fun KeepGoingButton(onClick: () -> Unit) {
    Column {
        Spacer(modifier = Modifier.weight(1F))
        KeepGoingButton(
            modifier = Modifier.padding(bottom = 20.dp),
            onClick = onClick,
        )
    }
}

@Composable
private fun KeepGoingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    GradientButton(
        modifier =
            modifier
                .fillMaxWidth()
                .aspectRatio(6 / 1F),
        shape = RoundedCornerShape(12.dp),
        text = stringResource(id = R.string.keep_going),
        fontColor = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        gradient = Brush.horizontalGradient(PrimaryColors),
        onClick = onClick,
    )
}

@Preview
@Composable
private fun ProfileCreationPreview() {
    ProfileCreationIntroduceScreen(
        navController = rememberNavController(),
    )
}
