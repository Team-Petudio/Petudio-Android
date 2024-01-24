package com.composition.damoa.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.model.User.SocialType
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.presentation.screens.login.state.LoginUiEvent
import com.composition.damoa.presentation.screens.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _loginUiEvent = MutableSharedFlow<LoginUiEvent>()
    val loginUiEvent = _loginUiEvent.asSharedFlow()

    fun login(
        socialType: SocialType,
        socialAccessToken: String,
        fcmToken: String,
    ) {
        viewModelScope.launch {
            when (userRepository.login(socialType, socialAccessToken, fcmToken)) {
                is Success -> _loginUiEvent.emit(LoginUiEvent.LOGIN_SUCCESS)
                is Failure, NetworkError, is Unexpected -> _loginUiEvent.emit(LoginUiEvent.LOGIN_FAILURE)
            }
        }
    }
}