package com.composition.damoa.presentation.screens.store.state

import com.composition.damoa.data.model.PurchaseItem
import com.composition.damoa.presentation.common.base.BaseUiState

data class TicketPurchaseUiState(
    override val state: State = State.NONE,
    val purchaseItems: List<PurchaseItem> = emptyList(),
    val ticketCount: Int = 0,
) : BaseUiState()
