package com.composition.damoa.presentation.screens.profileCreation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.PetudioTheme

class ProfileCreationActivity : ComponentActivity() {
    private val launchPhotoPicker = PhotoPicker(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCreation(onPhotoSelect = {
                launchPhotoPicker.launchPhotoPicker(this) { images ->
                    // TODO(사진 선택 결과를 처리한다.)
                }
            })
        }
    }
}

@Composable
private fun ProfileCreation(
    onPhotoSelect: () -> Unit = {},
) {
    PetudioTheme {
        val activity = LocalContext.current as? Activity
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                ProfileCreationTopAppBar(
                    onNavigationClick = { if (!navController.popBackStack()) activity?.finish() },
                )
            },
        ) { padding ->
            ProfileCreationNavHost(
                modifier = Modifier.padding(top = padding.calculateTopPadding()),
                navController = navController,
                onPhotoSelect = onPhotoSelect,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProfileCreationTopAppBar(onNavigationClick: () -> Unit = {}) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_back),
                    tint = Color.Black,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
    )
}

@Composable
private fun ProfileCreationNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onPhotoSelect: () -> Unit = {},
    startDestination: ProfileCreationScreen = ProfileCreationScreen.PROFILE_CREATION_INTRODUCE,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route,
    ) {
        composable(ProfileCreationScreen.PROFILE_CREATION_INTRODUCE.route) {
            ProfileCreationIntroduceScreen(navController = navController)
        }
        composable(ProfileCreationScreen.PET_PHOTO_SELECT.route) {
            PetPhotoSelectScreen(navController = navController)
        }
        composable(ProfileCreationScreen.PET_NAME.route) {
            PetNameScreen(navController = navController)
        }
        composable(ProfileCreationScreen.PET_COLOR.route) {
            PetColorScreen(navController = navController)
        }
        composable(ProfileCreationScreen.PHOTO_UPLOAD_INTRODUCE.route) {
            PhotoUploadIntroduceScreen(navController = navController, onClickPhotoUpload = onPhotoSelect)
        }
        composable(ProfileCreationScreen.PHOTO_UPLOAD_RESULT.route) {
            PhotoUploadResultScreen(navController = navController)
        }
        composable(ProfileCreationScreen.PAYMENT.route) {
            PaymentScreen(navController = navController)
        }
        composable(ProfileCreationScreen.PAYMENT_RESULT.route) {
            PaymentResultScreen(navController = navController)
        }
    }
}

enum class ProfileCreationScreen(
    val route: String,
) {
    PROFILE_CREATION_INTRODUCE("profileCreationIntroduce"),
    PET_PHOTO_SELECT("petPhotoSelect"),
    PET_NAME("petName"),
    PET_COLOR("petColor/{petType}"),
    PHOTO_UPLOAD_INTRODUCE("photoUploadIntroduce"),
    PHOTO_UPLOAD_RESULT("photoUploadResult"),
    PAYMENT("payment"),
    PAYMENT_RESULT("paymentResult"),
}
