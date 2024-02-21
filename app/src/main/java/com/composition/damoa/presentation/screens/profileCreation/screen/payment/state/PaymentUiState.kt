package com.composition.damoa.presentation.screens.profileCreation.screen.payment.state

import com.composition.damoa.presentation.common.base.BaseUiState

data class PaymentUiState(
    override val state: State = State.NONE,
    val ticketCount: Int = 0,
    val onPaymentClick: () -> Unit,
) : BaseUiState()