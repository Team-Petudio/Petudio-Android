package com.composition.damoa.presentation.screens.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.composition.damoa.R

enum class HomeBottomNavItem(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int,
) {
    ProfileConcept("aiProfile", R.drawable.ic_footprint, R.string.home_bottom_nav_item_ai_profile),
    Gallery("gallery", R.drawable.ic_gallery, R.string.home_bottom_nav_item_gallery),
    Profile("profile", R.drawable.ic_profile, R.string.home_bottom_nav_item_profile),
}
