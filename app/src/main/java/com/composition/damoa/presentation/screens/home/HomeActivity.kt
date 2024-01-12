package com.composition.damoa.presentation.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.PetudioTheme
import com.composition.damoa.presentation.ui.theme.Pink20
import com.composition.damoa.presentation.ui.theme.Pink40
import com.composition.damoa.presentation.ui.theme.Pink80
import com.composition.damoa.presentation.ui.theme.Purple60

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeScreen() {
    PetudioTheme {
        val homeNavController = rememberNavController()

        Scaffold(
            topBar = { HomeTopAppBar() },
            bottomBar = { HomeBottomNavigationBar(navController = homeNavController) },
        ) { padding ->
            HomeNavHost(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding(),
                        ),
                navController = homeNavController,
            )
        }
    }
}

@Composable
private fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeBottomNavItem.AiProfile.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(HomeBottomNavItem.AiProfile.route) { /* Home Screen UI */ }
        composable(HomeBottomNavItem.Gallery.route) { /* Search Screen UI */ }
        composable(HomeBottomNavItem.Profile.route) { /* Profile Screen UI */ }
    }
}

@Composable
private fun HomeTopAppBar() {
    TopAppBar(
        title = { HomeAppBarTitle() },
        navigationIcon = { HomeAppBarIcon() },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
    )
}

@Composable
fun HomeAppBarTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.offset(x = (-12).dp),
        text = stringResource(id = R.string.en_app_name),
        style =
            TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                brush =
                    Brush.horizontalGradient(
                        listOf(
                            Purple60,
                            Pink80,
                            Pink40,
                            Pink20,
                        ),
                    ),
            ),
    )
}

@Composable
fun HomeAppBarIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = null,
        tint = Color.Unspecified,
    )
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
fun HomePreview() {
    HomeScreen()
}

@Preview(showBackground = true)
@Composable
fun HomeBottomNavigationBarPreview() {
    PetudioTheme {
        HomeBottomNavigationBar()
    }
}
