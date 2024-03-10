package com.composition.damoa.presentation.screens.giftcard.state

import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.presentation.common.base.BaseUiState
import java.time.LocalDateTime

data class GiftCardUiState(
    override val state: State = State.NONE,
    val usableGiftCards: List<GiftCard> = emptyList(),
    val unUsableGiftCards: List<GiftCard> = emptyList(),
    val enteredGiftCardNumber: String = "",
    val selectedGiftCard: GiftCard? = null,
    val onGiftCardNumberChanged: (String) -> Unit,
    val onGiftCardEnteringDone: () -> Unit,
    val onGiftCardDetailShow: (GiftCard) -> Unit,
    val onGiftCardDetailDismiss: () -> Unit,
) : BaseUiState()