package com.composition.damoa.presentation.screens.store.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.composition.damoa.data.model.PurchaseItem
import com.composition.damoa.presentation.common.components.DonationDescription
import com.composition.damoa.presentation.common.components.PaymentInformationList
import com.composition.damoa.presentation.common.components.PolicyButtonList
import com.composition.damoa.presentation.common.extensions.navigateToPrivacy
import com.composition.damoa.presentation.common.extensions.navigateToTermOfUse
import com.composition.damoa.presentation.screens.store.state.TicketPurchaseUiState


@Composable
fun StoreContent(
    modifier: Modifier = Modifier,
    ticketPurchaseUiState: TicketPurchaseUiState,
    onPurchaseClick: (PurchaseItem) -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        StoreTitle()
        UserOwnTicket(
            modifier = Modifier.padding(top = 20.dp),
            ticketCount = ticketPurchaseUiState.ticketCount
        )
        DonationDescription(
            modifier = Modifier.padding(top = 12.dp, bottom = 20.dp)
        )
        PurchaseItemList(
            purchaseItems = ticketPurchaseUiState.purchaseItems,
            onPurchaseClick = onPurchaseClick,
        )
        Spacer(modifier = Modifier.weight(1F))
        PaymentInformationList()
        PolicyButtonList(
            modifier = Modifier.padding(top = 14.dp),
            onTermOfUseClick = { context.navigateToTermOfUse() },
            onPrivacyClick = { context.navigateToPrivacy() },
        )
    }
}