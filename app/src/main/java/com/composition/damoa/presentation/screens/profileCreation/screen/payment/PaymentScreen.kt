package com.composition.damoa.presentation.screens.profileCreation.screen.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composition.damoa.presentation.screens.profileCreation.screen.payment.component.PaymentButton
import com.composition.damoa.presentation.screens.profileCreation.screen.payment.component.PaymentContent
import com.composition.damoa.presentation.screens.profileCreation.screen.payment.state.PaymentUiState


@Composable
fun PaymentScreen(
    modifier: Modifier = Modifier,
    paymentUiState: PaymentUiState,
) {
    Surface(
        color = Color.White,
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize(),
    ) {
        PaymentContent(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 20.dp, bottom = 100.dp),
        )
        PaymentButton(
            modifier = Modifier.padding(bottom = 20.dp),
            onClick = { paymentUiState.onPaymentClick() },
        )
    }
}
