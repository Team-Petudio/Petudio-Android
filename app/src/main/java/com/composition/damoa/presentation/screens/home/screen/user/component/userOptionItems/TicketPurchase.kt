package com.composition.damoa.presentation.screens.home.screen.user.component.userOptionItems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigDescription


@Composable
fun TicketPurchase(
    myOwnTicketCount: Int,
    onClick: () -> Unit,
) {
    UserOptionItem(
        item = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                BigDescription(
                    descriptionRes = R.string.profile_option_ticket,
                    fontColor = Color.Black,
                )
                Ticket(ticketCount = myOwnTicketCount)
            }
        },
        onClick = onClick,
    )
}

@Composable
private fun Ticket(
    modifier: Modifier = Modifier,
    ticketCount: Int,
) {
    Row(
        modifier = modifier.padding(end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.padding(end = 4.dp),
            painter = painterResource(id = R.drawable.ic_ticket),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            text = String.format("%,d", ticketCount),
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
    }
}