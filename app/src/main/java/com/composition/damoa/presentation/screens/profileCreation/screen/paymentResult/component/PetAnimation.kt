package com.composition.damoa.presentation.screens.profileCreation.screen.paymentResult.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.composition.damoa.R
import com.composition.damoa.data.model.PetType


@Composable
fun PetAnimation(
    modifier: Modifier = Modifier,
    petType: PetType,
) {
    val petRawRes = when (petType) {
        PetType.DOG -> R.raw.anim_dog
        PetType.CAT -> R.raw.anim_cat
    }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(petRawRes))
    LottieAnimation(
        composition = composition,
        modifier =
        modifier
            .fillMaxWidth()
            .aspectRatio(1F),
        iterations = LottieConstants.IterateForever,
    )
}