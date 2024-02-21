package com.composition.damoa.presentation.screens.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.composition.damoa.data.model.PurchaseItem
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.screens.login.LoginActivity
import com.composition.damoa.presentation.screens.store.component.StoreScreen
import com.composition.damoa.presentation.screens.store.state.StoreUiEvent
import com.composition.damoa.presentation.screens.store.state.StoreUiEvent.TOKEN_EXPIRED
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class StoreActivity : ComponentActivity() {
    private val viewModel: StoreViewModel by viewModels()
    private lateinit var billingClient: BillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreScreen(
                viewModel = viewModel,
                onPurchaseClick = ::launchPurchaseFlow,
            )
            initBillingClient()
        }

        viewModel.event.collectEvent()
    }

    private fun SharedFlow<StoreUiEvent>.collectEvent() {
        onUi {
            collectLatest { event ->
                when (event) {
                    TOKEN_EXPIRED -> {
                        LoginActivity.startActivity(this@StoreActivity)
                        finish()
                    }
                }
            }
        }
    }

    private fun initBillingClient() {
        val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                // TODO: 구매 완료시 purchases에 구매 정보가 담겨 있다.
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
            context.startActivity(Intent(context, StoreActivity::class.java))
        }
    }
}
