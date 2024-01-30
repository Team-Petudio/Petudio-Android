package com.composition.damoa.presentation.common.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    GradientButton(
        modifier = modifier.height(52.dp),
        text = stringResource(R.string.login),
        shape = RoundedCornerShape(12.dp),
        fontSize = 16.sp,
        onClick = onClick,
    )
}