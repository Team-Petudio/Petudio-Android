package com.composition.damoa.presentation.screens.pointCharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.TokenExpired
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.presentation.common.base.BaseUiState.State
import com.composition.damoa.presentation.screens.pointCharge.state.PointChargeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PointChargeViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _pointChargeUiState = MutableStateFlow(PointChargeUiState())
    val pointChargeUiState = _pointChargeUiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        fetchPoint()
    }

    private fun fetchPoint() {
        viewModelScope.launch {
            _pointChargeUiState.value = pointChargeUiState.value.copy(state = State.LOADING)
            when (val user = userRepository.getUser()) {
                is Success -> _pointChargeUiState.value = pointChargeUiState.value.copy(
                    state = State.SUCCESS,
                    point = user.data.point,
                )

                NetworkError -> _pointChargeUiState.value = pointChargeUiState.value.copy(state = State.NETWORK_ERROR)
                TokenExpired -> _event.emit(Event.TOKEN_EXPIRED)
                is Failure, is Unexpected -> _pointChargeUiState.value = pointChargeUiState.value.copy(
                    state = State.NONE
                )
            }
        }
    }

    enum class Event {
        TOKEN_EXPIRED,
    }
}