package com.composition.damoa.presentation.screens.home.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
import com.composition.damoa.presentation.common.extensions.navigateSingleTop
import com.composition.damoa.presentation.ui.theme.Purple60


enum class HomeBottomNavItem(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int,
) {
    ProfileConcept("aiProfile", R.drawable.ic_footprint, R.string.home_bottom_nav_item_ai_profile),
    Gallery("gallery", R.drawable.ic_gallery, R.string.home_bottom_nav_item_gallery),
    Profile("profile", R.drawable.ic_profile, R.string.home_bottom_nav_item_profile);
}


@Composable
fun HomeBottomNavigationBar(
    navController: NavController = rememberNavController(),
) {
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
                    navController.navigateSingleTop(item.route)
                },
                icon = { BottomNavigationBarIcon(item, isSelected) },
                label = { BottomNavigationBarTitle(item, isSelected) },
            )
        }
    }
}

@Composable
private fun BottomNavigationBarIcon(
    item: HomeBottomNavItem,
    isSelected: Boolean,
) {
    Icon(
        painter = painterResource(id = item.iconRes),
        contentDescription = stringResource(id = item.labelRes),
        tint = if (isSelected) Purple60 else Color.Black,
        modifier = Modifier
            .size(25.dp)
            .padding(bottom = 4.dp),
    )
}

@Composable
private fun BottomNavigationBarTitle(
    item: HomeBottomNavItem,
    isSelected: Boolean,
) {
    Text(
        text = stringResource(id = item.labelRes),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = if (isSelected) Purple60 else Color.Black,
        modifier = Modifier.padding(top = 20.dp),
    )
}
