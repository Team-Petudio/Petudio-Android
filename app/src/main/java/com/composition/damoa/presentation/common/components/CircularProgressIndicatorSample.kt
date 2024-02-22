package com.composition.damoa.presentation.common.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composition.damoa.presentation.ui.theme.Purple60


@Composable
fun CircularLoadingBar(size: Dp = 50.dp) {
    CircularProgressIndicator(
        modifier = Modifier.size(size),
        color = Purple60,
        strokeWidth = 5.dp,
    )
}
