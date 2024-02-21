package com.composition.damoa.presentation.screens.store.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.ui.theme.Purples


@Composable
fun PurchaseButton(
    modifier: Modifier = Modifier,
    price: String,
    onClick: () -> Unit,
) {
    val purples by remember { mutableStateOf(Purples.reversed()) }

    GradientButton(
        modifier = modifier.size(90.dp, 44.dp),
        text = price,
        shape = RoundedCornerShape(12.dp),
        gradient = Brush.horizontalGradient(purples),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        onClick = onClick,
    )
}