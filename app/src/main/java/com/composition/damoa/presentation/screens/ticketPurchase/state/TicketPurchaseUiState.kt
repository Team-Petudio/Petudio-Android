package com.composition.damoa.presentation.screens.ticketPurchase.state

import com.android.billingclient.api.ProductDetails
import com.composition.damoa.presentation.common.base.BaseUiState

data class TicketPurchaseUiState(
    override val state: State = State.NONE,
    val productDetails: List<ProductDetails> = emptyList(),
    val ticketCount: Int = 0,
    val couponSerial: String = "",
) : BaseUiState()