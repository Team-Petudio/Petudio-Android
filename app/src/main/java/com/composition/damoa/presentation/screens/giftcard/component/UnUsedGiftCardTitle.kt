package com.composition.damoa.presentation.screens.giftcard.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.MediumTitle


@Composable
fun UnUsedGiftCardTitle(
    modifier: Modifier = Modifier,
) {
    MediumTitle(
        modifier = modifier,
        titleRes = R.string.unused_giftcard_title
    )
}