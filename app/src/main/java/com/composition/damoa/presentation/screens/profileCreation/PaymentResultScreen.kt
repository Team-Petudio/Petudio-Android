package com.composition.damoa.presentation.screens.profileCreation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.composition.damoa.R
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.MediumDescription

@Composable
fun PaymentResultScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    petType: PetType = PetType.DOG,
) {
    Surface(
        color = Color.White,
        modifier =
        modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize(),
    ) {
        PaymentResultContent(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            petType = petType,
        )
    }
}

@Composable
private fun PaymentResultContent(
    modifier: Modifier = Modifier,
    petType: PetType,
) {
    Column(modifier) {
        BigTitle(
            titleRes = R.string.payment_complete,
            modifier = Modifier.padding(top = 20.dp),
        )
        MediumDescription(
            descriptionRes = R.string.payment_complete_desc,
            modifier = Modifier.padding(top = 12.dp),
        )
        PetAnimation(Modifier.padding(top = 20.dp), petType)
    }
}

@Composable
private fun PetAnimation(
    modifier: Modifier = Modifier,
    petType: PetType,
) {
    val petRawRes =
        when (petType) {
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

@Preview
@Composable
private fun PaymentResultDogModeScreenPreview() {
    PaymentResultScreen(
        navController = rememberNavController(),
        petType = PetType.DOG,
    )
}

@Preview
@Composable
private fun PaymentResultCatModeScreenPreview() {
    PaymentResultScreen(
        navController = rememberNavController(),
        petType = PetType.CAT,
    )
}
