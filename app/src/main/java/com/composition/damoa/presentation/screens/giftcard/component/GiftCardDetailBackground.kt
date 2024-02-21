package com.composition.damoa.presentation.screens.giftcard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.composition.damoa.R


@Composable
fun GiftCardDetailBackground(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
        painter = painterResource(R.drawable.bg_download_gift_card),
        contentDescription = null,
    )
}