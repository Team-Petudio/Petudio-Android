package com.composition.damoa.presentation.screens.giftcard.component

import GiftCardItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.data.model.GiftCard


@Composable
fun GiftCardList(
    modifier: Modifier = Modifier,
    giftCards: List<GiftCard>,
    onGiftCardDetailClick: (giftCard: GiftCard) -> Unit,
) {
    val usableGiftCards = giftCards.filter { !it.isUsed and !it.isExpired }
    val unUsableGiftCards = giftCards.filter { it.isUsed or it.isExpired }

    LazyColumn(
        modifier = modifier.heightIn(max = 10000.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 30.dp),
    ) {
        item { UnUsedGiftCardTitle() }
        items(
            items = usableGiftCards,
            key = { it.giftCode }
        ) { giftCard -> GiftCardItem(giftCard = giftCard, onGiftCardDetailClick = { onGiftCardDetailClick(giftCard) }) }

        item { UnusableGiftCardTitle(modifier = Modifier.padding(top = 30.dp)) }
        items(
            items = unUsableGiftCards,
            key = { it.giftCode }
        ) { giftCard ->
            GiftCardItem(giftCard = giftCard)
        }
    }
}