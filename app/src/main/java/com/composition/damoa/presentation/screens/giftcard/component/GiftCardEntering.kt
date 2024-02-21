package com.composition.damoa.presentation.screens.giftcard.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.SmallTitle
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiState


@Composable
fun GiftCardEntering(giftCardUiState: GiftCardUiState) {
    SmallTitle(
        modifier = Modifier.padding(top = 20.dp),
        titleRes = R.string.gift_card_number_title,
    )
    GiftNumberInput(
        modifier = Modifier.padding(top = 12.dp, bottom = 20.dp),
        couponSerial = giftCardUiState.enteredGiftCardNumber,
        giftCardNumberChanged = { giftCardNumber -> giftCardUiState.onGiftCardNumberChanged(giftCardNumber) },
        onGiftCardEnteringDone = { giftCardUiState.onGiftCardEnteringDone() },
    )
}