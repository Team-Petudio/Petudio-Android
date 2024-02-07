package com.composition.damoa.presentation.screens.ticketPurchase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.composition.damoa.R
import com.composition.damoa.data.model.PurchaseItem
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.DonationDescription
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.components.PaymentInformationList
import com.composition.damoa.presentation.common.components.PolicyButtonList
import com.composition.damoa.presentation.common.components.SmallTitle
import com.composition.damoa.presentation.common.components.TinyTitle
import com.composition.damoa.presentation.common.extensions.navigateToPrivacy
import com.composition.damoa.presentation.common.extensions.navigateToTermOfUse
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.screens.login.LoginActivity
import com.composition.damoa.presentation.screens.ticketPurchase.TicketPurchaseViewModel.Event
import com.composition.damoa.presentation.screens.ticketPurchase.state.TicketPurchaseUiState
import com.composition.damoa.presentation.ui.theme.Gray10
import com.composition.damoa.presentation.ui.theme.PetudioTheme
import com.composition.damoa.presentation.ui.theme.Purple50
import com.composition.damoa.presentation.ui.theme.Purples
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class TicketPurchaseActivity : ComponentActivity() {
    private val viewModel: TicketPurchaseViewModel by viewModels()
    private lateinit var billingClient: BillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicketPurchaseScreen(
                viewModel = viewModel,
                onPurchaseClick = ::launchPurchaseFlow,
            )
            initBillingClient()
        }
    }

    private fun initBillingClient() {
        val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                // TODO: 구매 완료시 purchase에 구매 정보가 담겨 있다.
                // TODO: 필요한 정보를 서버로 전송하기
            } else if (billingResult.responseCode != BillingClient.BillingResponseCode.USER_CANCELED) {
                Log.d("buna", "구글 결제 에러 : ${billingResult.responseCode} ${billingResult.debugMessage}")
            }
        }

        billingClient = BillingClient.newBuilder(this)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        startBillingConnection()
    }

    fun startBillingConnection(
        retryCount: Int = 3,
        tryCount: Int = 0,
    ) {
        if (tryCount >= retryCount) return

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    showProducts()
                } else {
                    Log.e("buna", billingResult.debugMessage)
                }
            }

            override fun onBillingServiceDisconnected() {
                startBillingConnection(retryCount, tryCount + 1)
            }
        })
    }

    private fun showProducts() {
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("com.composition.damoa.ticket")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("com.composition.damoa.giftcard")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),
        )

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient.queryProductDetailsAsync(params) { billingResult, prodDetails ->
            if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) return@queryProductDetailsAsync
            viewModel.updateProductDetails(prodDetails)
        }
    }

    private fun launchPurchaseFlow(purchaseItem: PurchaseItem) {
        val productDetailsParamsList = listOf(
            ProductDetailsParams.newBuilder()
                .setProductDetails(purchaseItem.productDetails)
                .build()
        )
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()
        val billingResult = billingClient.launchBillingFlow(this, billingFlowParams)
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, TicketPurchaseActivity::class.java))
        }
    }
}

@Composable
private fun TicketPurchaseScreen(
    viewModel: TicketPurchaseViewModel,
    onPurchaseClick: (PurchaseItem) -> Unit,
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
                    .verticalScroll(rememberScrollState())
                    .padding(top = padding.calculateTopPadding()),
                ticketPurchaseUiState = ticketPurchaseUiState,
                onPurchaseClick = onPurchaseClick,
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
    onPurchaseClick: (PurchaseItem) -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        TicketPurchaseTitle()
        UserOwnTicket(ticketCount = ticketPurchaseUiState.ticketCount)
        DonationDescription(modifier = Modifier.padding(top = 12.dp, bottom = 20.dp))
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

@Composable
private fun TicketPurchaseTitle() {
    BigTitle(titleRes = R.string.ticket_purchase_title)
}

@Composable
private fun UserOwnTicket(ticketCount: Int) {
    TicketCount(
        modifier = Modifier.padding(top = 20.dp),
        title = stringResource(id = R.string.my_ticket_count),
        ticketCount = ticketCount,
    )
}

@Composable
private fun PurchaseItemList(
    modifier: Modifier = Modifier,
    purchaseItems: List<PurchaseItem>,
    onPurchaseClick: (PurchaseItem) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        purchaseItems.forEach { productDetail ->
            PurchaseItem(
                purchaseItems = productDetail,
                onPurchaseClick = onPurchaseClick,
            )
        }
    }
}

@Composable
private fun PurchaseItem(
    modifier: Modifier = Modifier,
    purchaseItems: PurchaseItem,
    onPurchaseClick: (PurchaseItem) -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = { onPurchaseClick(purchaseItems) })
            .fillMaxWidth()
            .border(2.dp, Purple50, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        when (purchaseItems.category) {
            PurchaseItem.Category.TICKET -> Ticket(modifier.weight(1F))
            PurchaseItem.Category.GIFT_CARD -> GiftCard(modifier.weight(1F))
        }

        PurchaseButton(
            price = purchaseItems.productDetails.oneTimePurchaseOfferDetails?.formattedPrice ?: "ERROR",
            onClick = { onPurchaseClick(purchaseItems) },
        )
    }
}

@Composable
private fun PurchaseButton(
    modifier: Modifier = Modifier,
    price: String,
    onClick: () -> Unit = {},
) {
    val purples by remember { mutableStateOf(Purples.reversed()) }

    GradientButton(
        modifier = modifier.size(90.dp, 44.dp),
        text = price,
        shape = RoundedCornerShape(12.dp),
        gradient = Brush.horizontalGradient(purples),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        onClick = onClick,
    )
}

@Composable
private fun TicketCount(
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
        Row(verticalAlignment = Alignment.CenterVertically) {
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
}

@Composable
private fun GiftCard(modifier: Modifier) {
    Row(
        modifier.padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(26.dp),
            painter = painterResource(id = R.drawable.ic_giftcard),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        TinyTitle(titleRes = R.string.item_giftcard)
    }
}

@Composable
private fun Ticket(modifier: Modifier) {
    Row(
        modifier.padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(26.dp),
            painter = painterResource(id = R.drawable.ic_ticket),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        TinyTitle(titleRes = R.string.item_ai_profile_ticket)
    }
}