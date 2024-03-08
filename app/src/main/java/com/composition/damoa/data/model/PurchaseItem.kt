package com.composition.damoa.data.model

import com.android.billingclient.api.ProductDetails


data class PurchaseItem(
    val productDetails: ProductDetails,
    val category: Category,
    val count: Int = 1,
) {
    enum class Category(private val id: String) {
        TICKET("ticket"),
        GIFT_CARD("giftcard");

        companion object {
            fun from(itemId: String): Category = entries.first { category ->
                itemId.contains(category.id)
            }
        }
    }
}