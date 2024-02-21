package com.composition.damoa.presentation.screens.store

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
import com.composition.damoa.presentation.screens.store.state.StoreUiEvent
import com.composition.damoa.presentation.screens.store.state.StoreUiEvent.TOKEN_EXPIRED
import com.composition.damoa.presentation.screens.store.state.TicketPurchaseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StoreViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _ticketPurchaseUiState = MutableStateFlow(TicketPurchaseUiState())
    val ticketPurchaseUiState = _ticketPurchaseUiState.asStateFlow()

    private val _event = MutableSharedFlow<StoreUiEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchTicket()
    }

    private fun fetchTicket() {
        viewModelScope.launch {
            _ticketPurchaseUiState.update { it.copy(state = State.LOADING) }
            when (val user = userRepository.getUser()) {
                is Success -> _ticketPurchaseUiState.update {
                    it.copy(state = State.SUCCESS, ticketCount = user.data.ticket)
                }

                NetworkError -> _ticketPurchaseUiState.update { it.copy(state = State.NETWORK_ERROR) }
                TokenExpired -> _event.emit(TOKEN_EXPIRED)
                is Failure, is Unexpected -> _ticketPurchaseUiState.update { it.copy(state = State.NONE) }
            }
        }
    }

    fun updateProductDetails(productDetails: List<ProductDetails>) {
        val purchaseItems = productDetails.map { productDetail ->
            PurchaseItem(
                productDetails = productDetail,
                category = PurchaseItem.Category.from(productDetail.productId),
            )
        }
        _ticketPurchaseUiState.update { it.copy(purchaseItems = purchaseItems) }
    }
}