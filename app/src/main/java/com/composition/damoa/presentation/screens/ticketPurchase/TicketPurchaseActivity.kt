package com.composition.damoa.presentation.screens.ticketPurchase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composition.damoa.R
import com.composition.damoa.data.model.Ticket
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.DonationDescription
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.components.PaymentInformationList
import com.composition.damoa.presentation.common.components.PolicyButtonList
import com.composition.damoa.presentation.common.components.SmallTitle
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.common.utils.ticketPurchaseItems
import com.composition.damoa.presentation.screens.login.LoginActivity
import com.composition.damoa.presentation.screens.ticketPurchase.TicketPurchaseViewModel.Event
import com.composition.damoa.presentation.screens.ticketPurchase.state.TicketPurchaseUiState
import com.composition.damoa.presentation.ui.theme.Gray10
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Gray40
import com.composition.damoa.presentation.ui.theme.PetudioTheme
import com.composition.damoa.presentation.ui.theme.Purple60
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TicketPurchaseActivity : ComponentActivity() {
    private val viewModel: TicketPurchaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicketPurchaseScreen(viewModel = viewModel)
        }
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, TicketPurchaseActivity::class.java)
    }
}

@Composable
private fun TicketPurchaseScreen(
    viewModel: TicketPurchaseViewModel,
) {
    PetudioTheme {
        val activity = LocalContext.current as? ComponentActivity
        val ticketPurchaseUiState by viewModel.ticketPurchaseUiState.collectAsStateWithLifecycle()

        activity?.onUi {
            viewModel.event.collectLatest { event ->
                when (event) {
                    Event.TOKEN_EXPIRED -> {
                        LoginActivity.startActivity(activity)
                        activity.finish()
                    }
                }
            }
        }

        Scaffold(
            topBar = { TicketPurchaseTopBar { activity?.finish() } },
        ) { padding ->
            TicketPurchaseContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding()),
                ticketPurchaseUiState = ticketPurchaseUiState,
                onCouponSerialChanged = viewModel::updateCouponSerial,
                onCouponSerialDone = viewModel::getTicketFromCouponSerial,
                ticketPurchaseItems = ticketPurchaseItems(),
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TicketPurchaseTopBar(onNavigationClick: () -> Unit = {}) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_back),
                    tint = Color.Black,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
    )
}

@Composable
private fun TicketPurchaseContent(
    modifier: Modifier = Modifier,
    ticketPurchaseUiState: TicketPurchaseUiState,
    onCouponSerialChanged: (String) -> Unit,
    onCouponSerialDone: () -> Unit,
    ticketPurchaseItems: List<Ticket>,
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        TicketPurchaseTitle()
        UserOwnTicket(ticketCount = ticketPurchaseUiState.ticketCount)
        GiftNumberInput(
            modifier = Modifier.padding(vertical = 20.dp),
            couponSerial = ticketPurchaseUiState.couponSerial,
            couponSerialChanged = onCouponSerialChanged,
            onCouponSerialDone = onCouponSerialDone,
        )
        DonationDescription(modifier = Modifier.padding(top = 12.dp, bottom = 20.dp))
        TicketPurchaseList(tickets = ticketPurchaseItems)
        Spacer(modifier = Modifier.weight(1F))
        PaymentInformationList()
        PolicyButtonList(modifier = Modifier.padding(top = 14.dp))
    }
}

@Composable
private fun TicketPurchaseTitle() {
    BigTitle(titleRes = R.string.ticket_purchase_title)
}

@Composable
private fun UserOwnTicket(ticketCount: Int) {
    TicketRow(
        modifier = Modifier.padding(top = 20.dp),
        title = stringResource(id = R.string.my_ticket_count),
        ticketCount = ticketCount,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun GiftNumberInput(
    modifier: Modifier = Modifier,
    couponSerial: String,
    couponSerialChanged: (String) -> Unit,
    onCouponSerialDone: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = couponSerial,
            onValueChange = { couponSerial ->
                if (couponSerial.length <= 32) couponSerialChanged(couponSerial)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1F),
            placeholder = { GiftNumberHint() },
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_gift),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Purple60,
                unfocusedBorderColor = Gray20,
                cursorColor = Purple60,
            ),
        )

        GradientButton(
            modifier = Modifier
                .size(90.dp, 52.dp)
                .padding(start = 12.dp),
            enabled = couponSerial.length == 32,
            text = stringResource(id = R.string.confirm),
            shape = RoundedCornerShape(12.dp),
            onClick = onCouponSerialDone,
        )
    }
}

@Composable
private fun GiftNumberHint() {
    Text(
        text = stringResource(R.string.gift_number_input),
        color = Gray40,
    )
}

@Composable
private fun TicketPurchaseList(
    modifier: Modifier = Modifier,
    tickets: List<Ticket>,
) {
    Column(modifier = modifier) {
        tickets.forEach { ticket ->
            TicketPurchaseItem(ticket = ticket)
        }
    }
}

@Composable
private fun TicketPurchaseItem(
    modifier: Modifier = Modifier,
    ticket: Ticket,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Ticket(
            modifier = modifier.padding(vertical = 20.dp),
            ticketCount = ticket.ticketCount,
        )
        PurchaseButton(price = ticket.price)
    }
}

@Composable
private fun PurchaseButton(
    modifier: Modifier = Modifier,
    price: Int,
    onClick: () -> Unit = {},
) {
    GradientButton(
        modifier = modifier.size(90.dp, 44.dp),
        text = String.format("%,d", price),
        shape = RoundedCornerShape(12.dp),
        gradient =
        Brush.verticalGradient(
            colors = listOf(Color.Black, Color.Black),
            startY = Float.POSITIVE_INFINITY,
            endY = 0F,
        ),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        onClick = onClick,
    )
}

@Composable
private fun TicketRow(
    modifier: Modifier,
    title: String,
    ticketCount: Int,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Gray10)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        SmallTitle(title = title)
        Ticket(ticketCount = ticketCount)
    }
}

@Composable
private fun Ticket(
    modifier: Modifier = Modifier,
    ticketCount: Int = 0,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(22.dp),
            painter = painterResource(id = R.drawable.ic_ticket),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            text = String.format("%,d", ticketCount),
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp),
            textAlign = TextAlign.Start,
        )
    }
}