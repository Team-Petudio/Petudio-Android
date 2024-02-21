package com.composition.damoa.presentation.screens.profileCreation.screen.payment.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.DonationDescription
import com.composition.damoa.presentation.common.components.PaymentInformationList
import com.composition.damoa.presentation.common.components.PolicyButtonList
import com.composition.damoa.presentation.common.extensions.navigateToPrivacy
import com.composition.damoa.presentation.common.extensions.navigateToTermOfUse


@Composable
fun PaymentContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(modifier) {
        BigTitle(titleRes = R.string.payment)
        PaymentItem(
            modifier = Modifier.padding(top = 32.dp),
            titleRes = R.string.payment_standard_title,
            descriptionRes = R.string.payment_standard_desc,
            ticketCount = 1,
        )
        DonationDescription(modifier = Modifier.padding(vertical = 28.dp))
        PaymentInformationList()
        PolicyButtonList(
            modifier = Modifier.padding(top = 14.dp),
            onTermOfUseClick = { context.navigateToTermOfUse() },
            onPrivacyClick = { context.navigateToPrivacy() },
        )
    }
}