package com.composition.damoa.presentation.screens.home.screen.gallery.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.ui.theme.PrimaryColors
import com.composition.damoa.presentation.ui.theme.Purple60


@Composable
fun EmptyAlbumScreen(
    modifier: Modifier = Modifier,
    onAiProfileClick: () -> Unit,
) {
    Column(
        modifier.padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        MediumTitle(
            fontColor = Purple60,
            titleRes = R.string.empty_album_content,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 40.dp),
        )
        GradientButton(
            modifier =
            Modifier.aspectRatio(6 / 1F),
            text = stringResource(id = R.string.create_ai_profile_button_content),
            gradient = Brush.linearGradient(PrimaryColors),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            onClick = onAiProfileClick,
        )
    }
}
