package com.composition.damoa.presentation.screens.home

import android.content.Context
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
import com.composition.damoa.data.model.User
import com.composition.damoa.presentation.screens.profileCreation.ProfileCreationActivity
import com.composition.damoa.presentation.ui.theme.PetudioTheme
import com.composition.damoa.presentation.ui.theme.Purple60
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(viewModel)
        }
    }
}

@Composable
private fun HomeScreen(
    viewModel: HomeViewModel,
) {
    PetudioTheme {
        val homeNavController = rememberNavController()
        val profileUiState by viewModel.profileUiState.collectAsStateWithLifecycle()
        val userUiState by viewModel.userUiState.collectAsStateWithLifecycle()

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
                user = userUiState.user
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
    user: User,
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
                petFeeds =
                listOf(
                    PetFeed(
                        id = 0,
                        title = "코코",
                        concept = "트렌디 룩북 컨셉",
                        isLike = true,
                        thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                        likeCount = 12300,
                    ),
                    PetFeed(
                        id = 0,
                        title = "코코",
                        concept = "트렌디 룩북 컨셉",
                        isLike = false,
                        thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                        likeCount = 2122,
                    ),
                    PetFeed(
                        id = 0,
                        title = "코코",
                        concept = "트렌디 룩북 컨셉",
                        isLike = false,
                        thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                        likeCount = 0,
                    ),
                ),
                albums =
                listOf(
                    Album(
                        id = 0,
                        title = "코코",
                        concept = "트렌디 룩북 컨셉",
                        thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                        photoUrls =
                        listOf(
                            "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                            "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                            "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                        ),
                        date = LocalDateTime.now(),
                    ),
                    Album(
                        id = 1,
                        title = "루다",
                        concept = "트렌디 룩북 컨셉",
                        thumbnailUrl = "https://mblogthumb-phinf.pstatic.net/MjAxODA3MTBfMTY4/MDAxNTMxMjAyODE5MDc2.kVMC7FdEN76iOiSRi672EUoT9bDm6WJnHn0YFIaglo8g.uAQXzhnbWUkd30hXVCQdGhga_J3hJgXdshwo4dM-Awog.JPEG.pp0_0/IMG_0475.jpg?type=w800",
                        photoUrls =
                        listOf(
                            "https://mblogthumb-phinf.pstatic.net/MjAxODA3MTBfMTY4/MDAxNTMxMjAyODE5MDc2.kVMC7FdEN76iOiSRi672EUoT9bDm6WJnHn0YFIaglo8g.uAQXzhnbWUkd30hXVCQdGhga_J3hJgXdshwo4dM-Awog.JPEG.pp0_0/IMG_0475.jpg?type=w800",
                            "https://mblogthumb-phinf.pstatic.net/MjAxODA3MTBfMTY4/MDAxNTMxMjAyODE5MDc2.kVMC7FdEN76iOiSRi672EUoT9bDm6WJnHn0YFIaglo8g.uAQXzhnbWUkd30hXVCQdGhga_J3hJgXdshwo4dM-Awog.JPEG.pp0_0/IMG_0475.jpg?type=w800",
                            "https://mblogthumb-phinf.pstatic.net/MjAxODA3MTBfMTY4/MDAxNTMxMjAyODE5MDc2.kVMC7FdEN76iOiSRi672EUoT9bDm6WJnHn0YFIaglo8g.uAQXzhnbWUkd30hXVCQdGhga_J3hJgXdshwo4dM-Awog.JPEG.pp0_0/IMG_0475.jpg?type=w800",
                        ),
                        date = LocalDateTime.now(),
                    ),
                ),
            )
        }
        composable(HomeBottomNavItem.Profile.route) { ProfileScreen(user = user) }
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
