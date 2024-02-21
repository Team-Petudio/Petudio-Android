package com.composition.damoa.presentation.screens.profileCreation.screen.payment.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.MediumDescription


@Composable
fun PaymentResultContent(
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