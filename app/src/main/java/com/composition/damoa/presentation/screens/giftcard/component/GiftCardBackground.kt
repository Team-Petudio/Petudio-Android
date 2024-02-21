package com.composition.damoa.presentation.screens.giftcard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.composition.damoa.R


@Composable
fun GiftCardBackground(isUsable: Boolean) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.bg_gift_card),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alpha = if (isUsable) 1F else 0.4F,
    )
}