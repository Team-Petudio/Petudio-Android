package com.composition.damoa.presentation.screens.giftcard.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composition.damoa.presentation.screens.giftcard.GiftCardViewModel
import com.composition.damoa.presentation.ui.theme.PetudioTheme


@Composable
fun GiftCardScreen(
    viewModel: GiftCardViewModel,
) {
    PetudioTheme {
        val activity = LocalContext.current as ComponentActivity
        val giftCardUiState by viewModel.giftCardUiState.collectAsStateWithLifecycle()

        Scaffold(
            topBar = { GiftCardTopBar { activity.finish() } },
        ) { padding ->
            GiftCardContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding()),
                giftCardUiState = giftCardUiState,
                onGiftCardDetailClick = { giftCard -> giftCardUiState.onGiftCardDetailShow(giftCard) },
            )

            GiftCardDetailDialog(giftCardUiState)
        }
    }
}