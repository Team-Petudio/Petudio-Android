package com.composition.damoa.presentation.screens.profileCreation.screen.payment.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.ui.theme.PrimaryColors


@Composable
fun PaymentButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(modifier) {
        Spacer(modifier = Modifier.weight(1F))
        GradientButton(
            modifier =
            Modifier
                .fillMaxWidth()
                .aspectRatio(6 / 1F),
            shape = RoundedCornerShape(12.dp),
            text = stringResource(id = R.string.payment),
            fontColor = Color.White,
            fontSize = 16.sp,
            enabled = true,
            fontWeight = FontWeight.Bold,
            gradient = Brush.horizontalGradient(PrimaryColors),
            onClick = onClick,
        )
    }
}