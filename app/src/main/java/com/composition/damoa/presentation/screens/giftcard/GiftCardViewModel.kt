package com.composition.damoa.presentation.screens.giftcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.TokenExpired
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.data.repository.interfaces.GiftCardRepository
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiEvent
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiftCardViewModel @Inject constructor(
    private val giftCardRepository: GiftCardRepository,
) : ViewModel() {
    private val _giftCardUiState = MutableStateFlow(
        GiftCardUiState(
            onGiftCardNumberChanged = ::updateGiftCardNumber,
            onGiftCardEnteringDone = ::getTicketFromCouponSerial,
            onGiftCardDetailShow = ::updateSelectedGiftCard,
            onGiftCardDetailDismiss = { updateSelectedGiftCard(null) },
        )
    )
    val giftCardUiState = _giftCardUiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<GiftCardUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        fetchGiftCards()
    }

    private fun updateGiftCardNumber(number: String) {
        _giftCardUiState.tryEmit(giftCardUiState.value.copy(enteredGiftCardNumber = number))
    }

    private fun getTicketFromCouponSerial() {

    }

    private fun fetchGiftCards() {
        viewModelScope.launch {
            when (val result = giftCardRepository.getGiftCards()) {
                is Success -> _giftCardUiState.emit(giftCardUiState.value.copy(giftCards = result.data))
                is Failure, is Unexpected -> _uiEvent.emit(GiftCardUiEvent.UNKNOWN_ERROR)
                TokenExpired -> _uiEvent.emit(GiftCardUiEvent.TOKEN_EXPIRED)
                NetworkError -> _uiEvent.emit(GiftCardUiEvent.NETWORK_ERROR)
            }
        }
    }

    private fun updateSelectedGiftCard(giftCard: GiftCard?) {
        _giftCardUiState.tryEmit(giftCardUiState.value.copy(selectedGiftCard = giftCard))
    }
}