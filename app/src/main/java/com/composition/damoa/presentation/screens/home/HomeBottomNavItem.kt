package com.composition.damoa.presentation.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class HomeBottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    AiProfile("aiProfile", Icons.Default.Home, "AI 프로필"),
    Gallery("gallery", Icons.Default.Search, "갤러리"),
    Setting("setting", Icons.Default.Person, "내 정보"),
}
