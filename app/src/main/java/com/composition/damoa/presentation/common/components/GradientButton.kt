package com.composition.damoa.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.Gray30
import com.composition.damoa.presentation.ui.theme.PrimaryColors

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.shape,
    text: String,
    fontColor: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    gradient: Brush = Brush.horizontalGradient(PrimaryColors),
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(),
        shape = shape,
        enabled = enabled,
        onClick = onClick,
    ) {
        val boxModifier = if (enabled) Modifier.background(gradient) else Modifier.background(Gray30)
        Box(
            modifier = boxModifier.fillMaxSize(),
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

@Preview
@Composable
private fun GradientButtonPreview() {
    GradientButton(
        modifier =
        Modifier
            .fillMaxWidth()
            .aspectRatio(6 / 1F),
        shape = RoundedCornerShape(12.dp),
        text = stringResource(id = R.string.keep_going),
        fontColor = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        gradient = Brush.horizontalGradient(PrimaryColors),
        onClick = { },
        enabled = false,
    )
}
