package com.composition.damoa.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.TokenExpired
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.presentation.common.base.BaseUiState
import com.composition.damoa.presentation.screens.home.state.ProfileUiState
import com.composition.damoa.presentation.screens.home.state.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val conceptRepository: ConceptRepository,
) : ViewModel() {
    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState = _userUiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        fetchProfileConcepts()
        fetchUser()
    }

    private fun fetchProfileConcepts() {
        viewModelScope.launch {
            _profileUiState.value = profileUiState.value.copy(state = BaseUiState.State.LOADING)
            when (val concepts = conceptRepository.getConcepts()) {
                is Success -> _profileUiState.value = profileUiState.value.copy(
                    state = BaseUiState.State.SUCCESS,
                    profileConcepts = concepts.data
                )

                NetworkError, TokenExpired -> _profileUiState.value = profileUiState.value.copy(
                    state = BaseUiState.State.NETWORK_ERROR
                )

                is Failure, is Unexpected -> _profileUiState.value = profileUiState.value.copy(
                    state = BaseUiState.State.NONE
                )
            }
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            _userUiState.value = userUiState.value.copy(state = BaseUiState.State.LOADING)
            when (val user = userRepository.getUser()) {
                is Success -> _userUiState.value = userUiState.value.copy(
                    state = BaseUiState.State.SUCCESS,
                    user = user.data
                )

                NetworkError, TokenExpired -> _userUiState.value = userUiState.value.copy(
                    state = BaseUiState.State.NETWORK_ERROR
                )

                is Failure, is Unexpected -> _userUiState.value = userUiState.value.copy(
                    state = BaseUiState.State.NONE
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            when (userRepository.logout()) {
                is Success -> _event.emit(Event.LOGOUT_SUCCESS)
                else -> _event.emit(Event.LOGOUT_FAILURE)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            when (userRepository.signOut()) {
                is Success -> _event.emit(Event.SIGN_OUT_SUCCESS)
                else -> _event.emit(Event.SIGN_OUT_FAILURE)

            }
        }
    }

    enum class Event {
        LOGOUT_SUCCESS,
        LOGOUT_FAILURE,
        SIGN_OUT_SUCCESS,
        SIGN_OUT_FAILURE,
    }
}