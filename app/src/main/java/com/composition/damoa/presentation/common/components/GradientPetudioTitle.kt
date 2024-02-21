package com.composition.damoa.presentation.common.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.PrimaryColors

@Composable
fun GradientPetudioTitle(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 36.sp,
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.en_app_name),
        style =
        TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Black,
            brush = Brush.horizontalGradient(PrimaryColors),
        ),
    )
}

@Composable
fun GradientPetudioSubTitle(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 36.sp,
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.app_sub_title),
        style =
        TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Black,
            brush = Brush.horizontalGradient(PrimaryColors),
        ),
    )
}
