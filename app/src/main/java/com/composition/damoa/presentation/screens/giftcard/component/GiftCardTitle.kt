package com.composition.damoa.presentation.screens.giftcard.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigTitle


@Composable
fun GiftCardTitle(
    modifier: Modifier = Modifier,
) {
    BigTitle(
        modifier = modifier,
        titleRes = R.string.item_giftcard
    )
}