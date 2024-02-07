package com.composition.damoa.presentation.screens.giftcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composition.damoa.R
import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.common.components.SmallDescription
import com.composition.damoa.presentation.common.components.SmallTitle
import com.composition.damoa.presentation.common.components.TinyTitle
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiEvent.NETWORK_ERROR
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiEvent.TOKEN_EXPIRED
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiEvent.UNKNOWN_ERROR
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiState
import com.composition.damoa.presentation.screens.login.LoginActivity
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Gray30
import com.composition.damoa.presentation.ui.theme.Gray40
import com.composition.damoa.presentation.ui.theme.PetudioTheme
import com.composition.damoa.presentation.ui.theme.Purple60
import com.composition.damoa.presentation.ui.theme.Purples
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class GiftCardActivity : ComponentActivity() {
    private val viewModel: GiftCardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GiftCardScreen(viewModel = viewModel)
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, GiftCardActivity::class.java))
        }
    }
}

@Composable
private fun GiftCardScreen(
    viewModel: GiftCardViewModel,
) {
    PetudioTheme {
        val activity = LocalContext.current as? ComponentActivity
        val giftCardUiState by viewModel.giftCardUiState.collectAsStateWithLifecycle()

        activity?.onUi {
            viewModel.uiEvent.collectLatest { event ->
                when (event) {
                    UNKNOWN_ERROR -> activity.showToast(R.string.unknown_error_message)
                    NETWORK_ERROR -> activity.showToast(R.string.network_failure_message)
                    TOKEN_EXPIRED -> LoginActivity.startActivity(activity)
                }
            }
        }

        Scaffold(
            topBar = { TicketPurchaseTopBar { activity?.finish() } },
        ) { padding ->
            GiftCardContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding()),
                giftCardUiState = giftCardUiState,
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
private fun GiftCardContent(
    modifier: Modifier = Modifier,
    giftCardUiState: GiftCardUiState,
) {
    LazyColumn(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        item { TicketPurchaseTitle() }
        item { GiftCardEntering(giftCardUiState) }
        item {
            GiftCards(
                modifier = Modifier.padding(top = 20.dp),
                giftCards = giftCardUiState.giftCards,
            )
        }
    }
}

@Composable
private fun TicketPurchaseTitle() {
    BigTitle(titleRes = R.string.item_giftcard)
}

@Composable
private fun GiftCardEntering(giftCardUiState: GiftCardUiState) {
    SmallTitle(
        modifier = Modifier.padding(top = 20.dp),
        titleRes = R.string.gift_card_number_title,
    )
    GiftNumberInput(
        modifier = Modifier.padding(top = 12.dp, bottom = 20.dp),
        couponSerial = giftCardUiState.enteredGiftCardNumber,
        giftCardNumberChanged = { giftCardNumber -> giftCardUiState.onGiftCardNumberChanged(giftCardNumber) },
        onGiftCardEnteringDone = { giftCardUiState.onGiftCardEnteringDone() },
    )
}

@Composable
private fun GiftNumberInput(
    modifier: Modifier = Modifier,
    couponSerial: String,
    giftCardNumberChanged: (String) -> Unit,
    onGiftCardEnteringDone: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val purples by remember { mutableStateOf(Purples.reversed()) }

        OutlinedTextField(
            value = couponSerial,
            onValueChange = { couponSerial ->
                if (couponSerial.length <= 32) giftCardNumberChanged(couponSerial)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .weight(1F)
                .heightIn(max = 52.dp),
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
            fontColor = Color.White,
            fontSize = 17.sp,
            enabled = couponSerial.length == 32,
            gradient = Brush.horizontalGradient(purples),
            disabledColor = Gray30,
            text = stringResource(id = R.string.confirm),
            shape = RoundedCornerShape(12.dp),
            onClick = onGiftCardEnteringDone,
        )
    }
}

@Composable
private fun GiftNumberHint() {
    Text(
        text = stringResource(R.string.gift_number_input_hint),
        color = Gray40,
    )
}

@Composable
private fun GiftCards(
    modifier: Modifier = Modifier,
    giftCards: List<GiftCard>,
) {
    val usableGiftCards by remember { mutableStateOf(giftCards.filter { !it.isUsed and !it.isExpired }) }
    val unUsableGiftCards by remember { mutableStateOf(giftCards.filter { it.isUsed or it.isExpired }) }

    LazyColumn(
        modifier = modifier.heightIn(max = 10000.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 30.dp),
    ) {
        item { UnUsedGiftCardTitle() }
        items(usableGiftCards) { giftCard -> GiftCardItem(giftCard = giftCard) }
        item { UnusableGiftCardTitle(modifier = Modifier.padding(top = 30.dp)) }
        items(unUsableGiftCards) { giftCard -> GiftCardItem(giftCard = giftCard) }
    }
}

@Composable
private fun UnUsedGiftCardTitle() {
    MediumTitle(titleRes = R.string.unused_giftcard_title)
}

@Composable
private fun UnusableGiftCardTitle(modifier: Modifier = Modifier) {
    MediumTitle(modifier = modifier, titleRes = R.string.unusable_giftcard_title)
}

@Composable
private fun GiftCardItem(
    modifier: Modifier = Modifier,
    giftCard: GiftCard,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(10 / 5F),
        shape = CutCornerShape(topStart = 20.dp, bottomEnd = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(),
    ) {
        Box {
            GiftCardBackground(isUsable = !giftCard.isExpired and !giftCard.isUsed)
            GiftCardForeground(giftCard)
        }
    }

}

@Composable
private fun GiftCardForeground(giftCard: GiftCard) {
    val expiredFormatter = DateTimeFormatter.ofPattern("~ yyyy.MM.dd HH:mm")
    val isUsable = !giftCard.isExpired and !giftCard.isUsed
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            MediumTitle(
                modifier = Modifier.padding(top = 12.dp),
                titleRes = R.string.giftcard_item_title,
                fontColor = Color.White
            )
            when {
                isUsable -> ExpiredDate(expiredFormatter, giftCard.expiredAt)
                giftCard.isExpired -> ExpiredMessage(expiredFormatter, giftCard.expiredAt)
                giftCard.isUsed -> UsedMessage()
            }
        }
        if (isUsable) GiftCardDetailButton()
    }
}

@Composable
private fun ExpiredDate(
    expiredFormatter: DateTimeFormatter,
    expiredAt: LocalDateTime,
) {
    Row(
        modifier = Modifier
            .padding(top = 6.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SmallDescription(descriptionRes = R.string.expired_date, fontColor = Purple60)
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = expiredFormatter.format(expiredAt),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Purple60,
        )
    }
}

@Composable
private fun ExpiredMessage(
    expiredFormatter: DateTimeFormatter,
    expiredAt: LocalDateTime,
) {
    Row(
        modifier = Modifier
            .padding(top = 6.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SmallDescription(descriptionRes = R.string.expired_message, fontColor = Purple60)
        Spacer(modifier = Modifier.size(8.dp))
        SmallDescription(description = expiredFormatter.format(expiredAt), fontColor = Purple60)
    }
}

@Composable
private fun UsedMessage() {
    Row(
        modifier = Modifier
            .padding(top = 6.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TinyTitle(titleRes = R.string.used_message, fontColor = Purple60)
    }
}

@Composable
private fun GiftCardBackground(isUsable: Boolean) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.bg_gift_card),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alpha = if (isUsable) 1F else 0.4F,
    )
}

@Composable
private fun GiftCardDetailButton(
    onClick: () -> Unit = {},
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Purple60,
        )
    }
}
