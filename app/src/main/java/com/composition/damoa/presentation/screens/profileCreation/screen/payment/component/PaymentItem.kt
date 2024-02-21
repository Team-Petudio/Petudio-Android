package com.composition.damoa.presentation.screens.profileCreation.screen.payment.component

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.Purple60
import com.composition.damoa.presentation.ui.theme.Purple80

@Composable
fun PaymentItem(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int,
    ticketCount: Int,
    onClick: () -> Unit = {},
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, Purple60),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
    ) {
        Column {
            PaymentTitle(titleRes = titleRes)
            PaymentDescription(descriptionRes = descriptionRes)
            PaymentPrice(ticketCount = ticketCount)
        }
    }
}

@Composable
private fun PaymentTitle(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = stringResource(titleRes),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Purple80,
        )
        Icon(
            modifier = Modifier.size(26.dp),
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = Purple60,
        )
    }
}

@Composable
private fun PaymentDescription(
    modifier: Modifier = Modifier,
    @StringRes descriptionRes: Int,
) {
    Text(
        text = stringResource(descriptionRes),
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray,
        modifier = modifier.padding(top = 4.dp),
    )
}

@Composable
private fun PaymentPrice(
    modifier: Modifier = Modifier,
    ticketCount: Int,
) {
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_ticket),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(24.dp),
            )
            Text(
                text = "%,d".format(ticketCount),
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
            )
        }
    }
}
