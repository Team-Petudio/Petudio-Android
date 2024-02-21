package com.composition.damoa.presentation.screens.giftcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.composition.damoa.R
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.screens.giftcard.component.GiftCardScreen
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiEvent
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiEvent.GIFT_CARD_USE_SUCCESS
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiEvent.NETWORK_ERROR
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiEvent.TOKEN_EXPIRED
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiEvent.UNKNOWN_ERROR
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiEvent.USED_GIFT_CARD_ERROR
import com.composition.damoa.presentation.screens.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class GiftCardActivity : ComponentActivity() {
    private val viewModel: GiftCardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GiftCardScreen(viewModel = viewModel)
        }

        viewModel.event.collectEvent()
    }

    private fun SharedFlow<GiftCardUiEvent>.collectEvent() {
        onUi {
            collectLatest { event ->
                when (event) {
                    UNKNOWN_ERROR -> showToast(R.string.unknown_error_message)
                    NETWORK_ERROR -> showToast(R.string.network_failure_message)
                    TOKEN_EXPIRED -> LoginActivity.startActivity(this@GiftCardActivity)
                    USED_GIFT_CARD_ERROR -> showToast(R.string.unusable_gift_card_error_message)
                    GIFT_CARD_USE_SUCCESS -> showToast(R.string.gift_card_use_success_message)
                }
            }
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, GiftCardActivity::class.java))
        }
    }
}
