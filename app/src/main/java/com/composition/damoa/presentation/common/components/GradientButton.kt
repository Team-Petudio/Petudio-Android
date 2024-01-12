package com.composition.damoa.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.shape,
    text: String,
    fontColor: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    gradient: Brush,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            ),
        contentPadding = PaddingValues(),
        shape = shape,
        onClick = onClick,
    ) {
        Box(
            modifier =
                Modifier
                    .background(gradient)
                    .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                color = fontColor,
                fontSize = fontSize,
                fontWeight = fontWeight,
            )
        }
    }
}
