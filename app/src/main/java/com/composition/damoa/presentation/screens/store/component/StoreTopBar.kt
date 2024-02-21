package com.composition.damoa.presentation.screens.store.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.composition.damoa.presentation.common.components.NavigationButton


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun StoreTopBar(
    onNavigationClick: () -> Unit,
) {
    TopAppBar(
        title = { },
        navigationIcon = { NavigationButton(onNavigationClick = onNavigationClick) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
    )
}
