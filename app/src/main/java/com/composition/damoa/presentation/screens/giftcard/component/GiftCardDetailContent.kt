package com.composition.damoa.presentation.screens.giftcard.component

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.composition.damoa.data.model.GiftCard


@Composable
fun GiftCardDetailContent(
    modifier: Modifier = Modifier,
    giftCard: GiftCard,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        GiftCardDetailBackground()
        GiftCardDetailForeground(giftCard = giftCard)
    }
}