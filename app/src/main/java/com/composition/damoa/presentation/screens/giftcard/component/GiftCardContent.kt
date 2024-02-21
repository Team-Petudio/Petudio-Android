package com.composition.damoa.presentation.screens.giftcard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiState


@Composable
fun GiftCardContent(
    modifier: Modifier = Modifier,
    giftCardUiState: GiftCardUiState,
    onGiftCardDetailClick: (giftCard: GiftCard) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        item { GiftCardTitle() }
        item { GiftCardEntering(giftCardUiState) }
        item {
            GiftCardList(
                modifier = Modifier.padding(top = 20.dp),
                giftCards = giftCardUiState.giftCards,
                onGiftCardDetailClick = onGiftCardDetailClick,
            )
        }
    }
}