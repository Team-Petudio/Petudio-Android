package com.composition.damoa.presentation.screens.profileCreation

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.DonationDescription
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.components.PaymentInformationList
import com.composition.damoa.presentation.common.components.PaymentItem
import com.composition.damoa.presentation.common.components.PolicyButtonList
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Gray40
import com.composition.damoa.presentation.ui.theme.Gray60
import com.composition.damoa.presentation.ui.theme.PrimaryColors

@Composable
fun PaymentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Surface(
        color = Color.White,
        modifier =
            modifier
                .background(Color.White)
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
    ) {
        PaymentContent(
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(top = 20.dp, bottom = 100.dp),
        )
        PaymentButton(
            modifier = Modifier.padding(bottom = 20.dp),
            onClick = {},
        )
    }
}

@Composable
private fun PaymentContent(modifier: Modifier = Modifier) {
    Column(modifier) {
        BigTitle(titleRes = R.string.payment)
        PaymentItem(
            modifier = Modifier.padding(top = 32.dp),
            titleRes = R.string.payment_standard_title,
            descriptionRes = R.string.payment_standard_desc,
            isDiscounted = true,
            originalPrice = 1500,
            discountedPrice = 880,
        )
        DonationDescription(modifier = Modifier.padding(vertical = 28.dp))
        PaymentInformationList()
        PolicyButtonList(modifier = Modifier.padding(top = 14.dp))
    }
}

@Composable
private fun PaymentButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(modifier) {
        Spacer(modifier = Modifier.weight(1F))
        GradientButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(6 / 1F),
            shape = RoundedCornerShape(12.dp),
            text = stringResource(id = R.string.payment),
            fontColor = Color.White,
            fontSize = 16.sp,
            enabled = true,
            fontWeight = FontWeight.Bold,
            gradient = Brush.horizontalGradient(PrimaryColors),
            onClick = onClick,
        )
    }
}

@Preview
@Composable
private fun PaymentScreenPreview() {
    PaymentScreen(navController = rememberNavController())
}
