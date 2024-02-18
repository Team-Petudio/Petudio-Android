package com.composition.damoa.presentation.screens.home.screen.gallery.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.LoginButton
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.ui.theme.Purple80


@Composable
fun LoginRequireGalleryScreen(
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        MediumTitle(
            fontColor = Purple80,
            titleRes = R.string.login_required_message,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 48.dp),
        )
        LoginButton(
            modifier = Modifier.fillMaxWidth(0.8F),
            onClick = onLoginClick,
        )
    }
}
