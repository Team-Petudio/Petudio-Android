package com.composition.damoa.presentation.screens.profileCreation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.utils.profileExamplePhotoUrls
import com.composition.damoa.presentation.ui.theme.AlertIconColor
import com.composition.damoa.presentation.ui.theme.Gray60
import com.composition.damoa.presentation.ui.theme.PetudioTheme
import com.composition.damoa.presentation.ui.theme.PrimaryColors

class ProfileCreationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCreation()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProfileCreation() {
    PetudioTheme {
        val activity = LocalContext.current as? Activity
        val navController = rememberNavController()
        val isFirstScreen by remember {
            derivedStateOf {
                navController.previousBackStackEntry == null
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (isFirstScreen) {
                                activity?.finish()
                            } else {
                                navController.navigateUp()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.navigate_back),
                                tint = Color.Black,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
                )
            },
        ) { padding ->
            ProfileCreationNavHost(
                modifier = Modifier.padding(top = padding.calculateTopPadding()),
                navController = navController,
            )
        }
    }
}

@Composable
private fun ProfileCreationNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ProfileCreationScreen.PROFILE_CREATION_INTRODUCE.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(ProfileCreationScreen.PROFILE_CREATION_INTRODUCE.route) { ProfileCreationIntroduceScreen() }
    }
}

@Composable
private fun ProfileCreationIntroduceScreen(modifier: Modifier = Modifier) {
    Surface(
        color = Color.White,
        modifier =
            modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
    ) {
        ProfileCreationIntroduceContent(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp),
        )
        KeepGoingButton()
    }
}

@Composable
private fun KeepGoingButton() {
    Column {
        Spacer(modifier = Modifier.weight(1F))
        KeepGoingButton(modifier = Modifier.padding(bottom = 20.dp))
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
private fun ProfileCreationGoodExample(modifier: Modifier = Modifier) {
    ProfileCreationIntroduce(
        modifier = modifier,
        title = stringResource(id = R.string.profile_create_good_example_intro_title),
        description = stringResource(id = R.string.profile_create_good_example_intro_desc),
        profileExamplePhotoUrls = profileExamplePhotoUrls(),
    )
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
private fun KeepGoingButton(modifier: Modifier = Modifier) {
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
    ) {
    }
}

enum class ProfileCreationScreen(
    val route: String,
) {
    PROFILE_CREATION_INTRODUCE("photoCreationIntroduce"),
    PET_PHOTO_GROUPS("petPhotoGroups"),
    PET_NAME("petName"),
    PET_COLOR("petColor"),
    PHOTO_UPLOAD_INTRODUCE("photoUploadIntroduce"),
    PHOTO_UPLOAD_RESULT("photoUploadResult"),
    PAYMENT("payment"),
    PAYMENT_RESULT("paymentResult"),
}

@Preview
@Composable
private fun ProfileCreationPreview() {
    ProfileCreation()
}
