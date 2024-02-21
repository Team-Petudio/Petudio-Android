package com.composition.damoa.presentation.screens.store.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composition.damoa.data.model.PurchaseItem
import com.composition.damoa.presentation.ui.theme.Purple50


@Composable
fun PurchaseItem(
    modifier: Modifier = Modifier,
    purchaseItems: PurchaseItem,
    onPurchaseClick: (PurchaseItem) -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = { onPurchaseClick(purchaseItems) })
            .fillMaxWidth()
            .border(2.dp, Purple50, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        when (purchaseItems.category) {
            PurchaseItem.Category.TICKET -> Ticket(modifier.weight(1F))
            PurchaseItem.Category.GIFT_CARD -> GiftCard(modifier.weight(1F))
        }

        PurchaseButton(
            price = purchaseItems.productDetails.oneTimePurchaseOfferDetails?.formattedPrice ?: "ERROR",
            onClick = { onPurchaseClick(purchaseItems) },
        )
    }
}