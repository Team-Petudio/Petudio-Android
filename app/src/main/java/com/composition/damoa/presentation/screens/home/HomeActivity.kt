package com.composition.damoa.presentation.screens.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.screens.home.HomeViewModel.Event.LOGOUT_FAILURE
import com.composition.damoa.presentation.screens.home.HomeViewModel.Event.LOGOUT_SUCCESS
import com.composition.damoa.presentation.screens.home.HomeViewModel.Event.SIGN_OUT_FAILURE
import com.composition.damoa.presentation.screens.home.HomeViewModel.Event.SIGN_OUT_SUCCESS
import com.composition.damoa.presentation.screens.home.state.AlbumUiState
import com.composition.damoa.presentation.screens.home.state.PetFeedUiState
import com.composition.damoa.presentation.screens.home.state.UserUiState
import com.composition.damoa.presentation.screens.login.LoginActivity
import com.composition.damoa.presentation.screens.profileCreation.ProfileCreationActivity
import com.composition.damoa.presentation.ui.theme.PetudioTheme
import com.composition.damoa.presentation.ui.theme.Purple60
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(viewModel)
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val homeStartIntent = Intent(context, HomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(homeStartIntent)
        }
    }
}

@Composable
private fun HomeScreen(
    viewModel: HomeViewModel,
) {
    PetudioTheme {
        val activity = LocalContext.current as? ComponentActivity
        val homeNavController = rememberNavController()
        val profileUiState by viewModel.profileUiState.collectAsStateWithLifecycle()
        val userUiState by viewModel.userUiState.collectAsStateWithLifecycle()
        val albumUiState by viewModel.albumUiState.collectAsStateWithLifecycle()
        val petFeedUiState by viewModel.petFeedUiState.collectAsStateWithLifecycle()

        activity?.onUi {
            viewModel.event.collectLatest { event ->
                when (event) {
                    LOGOUT_SUCCESS, SIGN_OUT_SUCCESS -> HomeActivity.startActivity(activity)
                    LOGOUT_FAILURE, SIGN_OUT_FAILURE -> activity.showToast(R.string.network_failure_message)
                }
            }
        }

        Scaffold(
            bottomBar = { HomeBottomNavigationBar(navController = homeNavController) },
        ) { padding ->
            HomeNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding(),
                    ),
                navController = homeNavController,
                profileConcepts = profileUiState.profileConcepts,
                albumUiState = albumUiState,
                petFeedUiState = petFeedUiState,
                userUiState = userUiState,
                onLogout = { viewModel.logout() },
                onSignOut = { viewModel.signOut() },
                onLogin = { activity?.run { LoginActivity.startActivity(this) } },
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeBottomNavItem.ProfileConcept.route,
    profileConcepts: List<ProfileConcept>,
    albumUiState: AlbumUiState,
    petFeedUiState: PetFeedUiState,
    userUiState: UserUiState,
    onLogout: () -> Unit = {},
    onSignOut: () -> Unit = {},
    onLogin: () -> Unit = {},
) {
    val context: Context = LocalContext.current
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(HomeBottomNavItem.ProfileConcept.route) {
            ProfileConceptScreen(
                profileConcepts = profileConcepts,
                onProfileConceptClick = { conceptId -> ProfileCreationActivity.startActivity(context, conceptId) }
            )
        }
        composable(HomeBottomNavItem.Gallery.route) {
            GalleryScreen(
                navController = navController,
                albumUiState = albumUiState,
                petFeedUiState = petFeedUiState,
                onLoginClick = onLogin,
            )
        }
        composable(HomeBottomNavItem.Profile.route) {
            ProfileScreen(
                userUiState = userUiState,
                onLogout = onLogout,
                onSignOut = onSignOut,
                onLogin = onLogin,
            )
        }
    }
}

@Composable
private fun HomeBottomNavigationBar(navController: NavController = rememberNavController()) {
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.white),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        HomeBottomNavItem.values().forEach { item ->
            val isSelected = currentRoute == item.route

            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute == item.route) return@BottomNavigationItem

                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = stringResource(id = item.labelRes),
                        tint = if (isSelected) Purple60 else Color.Black,
                        modifier =
                        Modifier
                            .size(25.dp)
                            .padding(bottom = 4.dp),
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.labelRes),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Purple60 else Color.Black,
                        modifier = Modifier.padding(top = 20.dp),
                    )
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeBottomNavigationBarPreview() {
    PetudioTheme {
        HomeBottomNavigationBar()
    }
}
