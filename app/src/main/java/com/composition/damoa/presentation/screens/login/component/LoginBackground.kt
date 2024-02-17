package com.composition.damoa.presentation.screens.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.composition.damoa.R


@Composable
fun LoginBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        val whiteGradientBrush = Brush.verticalGradient(
            colors = listOf(Color(0x80FFFFFF), Color.White, Color.White),
        )

        Image(
            painter = painterResource(id = R.drawable.img_photo_upload_good_example1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F),
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(whiteGradientBrush),
        )
    }
}