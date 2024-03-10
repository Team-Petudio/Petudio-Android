package com.composition.damoa.presentation.screens.giftcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.data.network.retrofit.callAdapter.Failure
import com.composition.damoa.data.network.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.network.retrofit.callAdapter.Success
import com.composition.damoa.data.network.retrofit.callAdapter.TokenExpired
import com.composition.damoa.data.network.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.repository.interfaces.GiftCardRepository
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiEvent
import com.composition.damoa.presentation.screens.giftcard.state.GiftCardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiftCardViewModel @Inject constructor(
    private val giftCardRepository: GiftCardRepository,
) : ViewModel() {
    private val _giftCardUiState = MutableStateFlow(
        GiftCardUiState(
            onGiftCardNumberChanged = ::updateGiftCardNumber,
            onGiftCardEnteringDone = ::useGiftCard,
            onGiftCardDetailShow = ::selectGiftCard,
            onGiftCardDetailDismiss = ::unselectGiftCard,
        )
    )
    val giftCardUiState = _giftCardUiState.asStateFlow()

    private val _event = MutableSharedFlow<GiftCardUiEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchGiftCards()
    }

    private fun fetchGiftCards() {
        viewModelScope.launch {
            when (val result = giftCardRepository.getGiftCards()) {
                is Success -> _giftCardUiState.update {
                    it.copy(
                        usableGiftCards = result.data.filter { giftCard -> !giftCard.isUsed and !giftCard.isExpired },
                        unUsableGiftCards = result.data.filter { giftCard -> giftCard.isUsed or giftCard.isExpired },
                    )
                }

                is Failure, is Unexpected -> _event.emit(GiftCardUiEvent.UNKNOWN_ERROR)
                TokenExpired -> _event.emit(GiftCardUiEvent.TOKEN_EXPIRED)
                NetworkError -> _event.emit(GiftCardUiEvent.NETWORK_ERROR)
            }
        }
    }

    private fun updateGiftCardNumber(number: String) {
        _giftCardUiState.update { it.copy(enteredGiftCardNumber = number) }
    }

    private fun useGiftCard() {
        viewModelScope.launch {
            val giftCode = giftCardUiState.value.enteredGiftCardNumber
            when (giftCardRepository.useGiftCard(giftCode)) {
                is Success -> {
                    _event.emit(GiftCardUiEvent.GIFT_CARD_USE_SUCCESS)
                    _giftCardUiState.update { it.copy(enteredGiftCardNumber = "") }
                    fetchGiftCards()
                }

                is Failure -> _event.emit(GiftCardUiEvent.USED_GIFT_CARD_ERROR)
                is Unexpected -> _event.emit(GiftCardUiEvent.UNKNOWN_ERROR)
                TokenExpired -> _event.emit(GiftCardUiEvent.TOKEN_EXPIRED)
                NetworkError -> _event.emit(GiftCardUiEvent.NETWORK_ERROR)
            }
        }
    }

    private fun selectGiftCard(giftCard: GiftCard?) {
        _giftCardUiState.update { it.copy(selectedGiftCard = giftCard) }
    }

    private fun unselectGiftCard() {
        selectGiftCard(null)
    }
}