package com.composition.damoa.presentation.screens.store.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.data.model.PurchaseItem


@Composable
fun PurchaseItemList(
    modifier: Modifier = Modifier,
    purchaseItems: List<PurchaseItem>,
    onPurchaseClick: (PurchaseItem) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        purchaseItems.forEach { productDetail ->
            PurchaseItem(
                purchaseItems = productDetail,
                onPurchaseClick = onPurchaseClick,
            )
        }
    }
}