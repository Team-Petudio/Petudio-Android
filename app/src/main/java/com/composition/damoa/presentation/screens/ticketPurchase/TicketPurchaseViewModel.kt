package com.composition.damoa.presentation.screens.ticketPurchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.TokenExpired
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.model.PurchaseItem
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.presentation.common.base.BaseUiState.State
import com.composition.damoa.presentation.screens.ticketPurchase.state.TicketPurchaseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TicketPurchaseViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _ticketPurchaseUiState = MutableStateFlow(TicketPurchaseUiState())
    val ticketPurchaseUiState = _ticketPurchaseUiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        fetchTicket()
    }

    private fun fetchTicket() {
        viewModelScope.launch {
            _ticketPurchaseUiState.value = ticketPurchaseUiState.value.copy(state = State.LOADING)
            when (val user = userRepository.getUser()) {
                is Success -> _ticketPurchaseUiState.value = ticketPurchaseUiState.value.copy(
                    state = State.SUCCESS,
                    ticketCount = user.data.ticket,
                )

                NetworkError -> _ticketPurchaseUiState.value =
                    ticketPurchaseUiState.value.copy(state = State.NETWORK_ERROR)

                TokenExpired -> _event.emit(Event.TOKEN_EXPIRED)
                is Failure, is Unexpected -> _ticketPurchaseUiState.value = ticketPurchaseUiState.value.copy(
                    state = State.NONE
                )
            }
        }
    }

    fun updateCouponSerial(number: String) {
        _ticketPurchaseUiState.value = _ticketPurchaseUiState.value.copy(enteredGiftCardNumber = number)
    }

    fun getTicketFromCouponSerial() {

    }

    fun updateProductDetails(productDetails: List<ProductDetails>) {
        val purchaseItems = productDetails.map { productDetail ->
            PurchaseItem(
                productDetails = productDetail,
                category = PurchaseItem.Category.from(productDetail.productId),
            )
        }
        _ticketPurchaseUiState.value = ticketPurchaseUiState.value.copy(purchaseItems = purchaseItems)
    }

    enum class Event {
        TOKEN_EXPIRED,
    }
}