package com.composition.damoa.presentation.screens.home.component

import android.app.Activity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.presentation.screens.home.screen.gallery.GalleryScreen
import com.composition.damoa.presentation.screens.home.screen.gallery.state.AlbumUiState
import com.composition.damoa.presentation.screens.home.screen.gallery.state.PetFeedUiState
import com.composition.damoa.presentation.screens.home.screen.profileConcept.ProfileConceptScreen
import com.composition.damoa.presentation.screens.home.screen.profileConcept.state.ProfileConceptUiState
import com.composition.damoa.presentation.screens.home.screen.user.UserScreen
import com.composition.damoa.presentation.screens.home.screen.user.state.UserUiState
import com.composition.damoa.presentation.screens.login.LoginActivity
import com.composition.damoa.presentation.screens.profileCreation.ProfileCreationActivity


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeBottomNavItem.ProfileConcept.route,
    profileConceptUiState: ProfileConceptUiState,
    albumUiState: AlbumUiState,
    petFeedUiState: PetFeedUiState,
    userUiState: UserUiState,
) {
    val context = LocalContext.current

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(HomeBottomNavItem.ProfileConcept.route) {
            ProfileConceptScreen(
                profileConceptUiState = profileConceptUiState,
                onProfileConceptClick = { conceptId ->
                    if (userUiState.isLogined) ProfileCreationActivity.startActivity(context, conceptId)
                    else LoginActivity.startActivity(context as Activity)
                }
            )
        }

        composable(HomeBottomNavItem.Gallery.route) {
            GalleryScreen(
                navController = navController,
                albumUiState = albumUiState,
                petFeedUiState = petFeedUiState,
                onLoginClick = userUiState.onLogin,
            )
        }

        composable(HomeBottomNavItem.Profile.route) {
            UserScreen(uiState = userUiState)
        }
    }
}