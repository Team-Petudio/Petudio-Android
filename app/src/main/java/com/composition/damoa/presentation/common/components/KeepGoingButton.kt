package com.composition.damoa.presentation.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.PrimaryColors


@Composable
fun KeepGoingButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.weight(1F))
        KeepGoingButton(
            modifier = Modifier.padding(bottom = 20.dp),
            enabled = enabled,
            onClick = onClick,
        )
    }
}

@Composable
fun KeepGoingButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    GradientButton(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(12.dp),
        text = stringResource(R.string.keep_going),
        fontColor = Color.White,
        fontSize = 16.sp,
        enabled = enabled,
        fontWeight = FontWeight.Bold,
        gradient = Brush.horizontalGradient(PrimaryColors),
        onClick = onClick,
    )
}
