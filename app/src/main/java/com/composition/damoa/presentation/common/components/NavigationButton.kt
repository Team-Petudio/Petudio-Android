package com.composition.damoa.presentation.common.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.composition.damoa.R

@Composable
fun NavigationButton(
    onNavigationClick: () -> Unit,
) {
    IconButton(onClick = onNavigationClick) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.navigate_back),
            tint = Color.Black,
        )
    }
}