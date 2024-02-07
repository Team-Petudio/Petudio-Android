package com.composition.damoa.presentation.screens.giftcard.state

import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.presentation.common.base.BaseUiState

data class GiftCardUiState(
    override val state: State = State.NONE,
    val giftCards: List<GiftCard> = emptyList(),
    val enteredGiftCardNumber: String = "",
    val onGiftCardNumberChanged: (String) -> Unit,
    val onGiftCardEnteringDone: () -> Unit,
) : BaseUiState()