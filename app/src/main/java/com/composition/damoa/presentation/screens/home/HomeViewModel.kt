package com.composition.damoa.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.TokenExpired
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.model.User
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.repository.interfaces.PetFeedRepository
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.presentation.common.base.BaseUiState.State
import com.composition.damoa.presentation.screens.home.state.AlbumUiState
import com.composition.damoa.presentation.screens.home.state.PetFeedUiState
import com.composition.damoa.presentation.screens.home.state.ProfileUiState
import com.composition.damoa.presentation.screens.home.state.UserUiState
import com.composition.damoa.presentation.screens.home.state.UserUiState.Companion.INVALID_USER_ID
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
    private val petFeedRepository: PetFeedRepository,
) : ViewModel() {
    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState = _userUiState.asStateFlow()

    private val _albumUiState = MutableStateFlow(AlbumUiState.dummy)
    val albumUiState = _albumUiState.asStateFlow()

    private val _petFeedUiState = MutableStateFlow(PetFeedUiState())
    val petFeedUiState = _petFeedUiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        fetchProfileConcepts()
        fetchUser { user -> fetchFeeds(user?.id ?: INVALID_USER_ID) }
    }

    private fun fetchProfileConcepts() {
        viewModelScope.launch {
            _profileUiState.value = profileUiState.value.copy(state = State.LOADING)
            when (val concepts = conceptRepository.getConcepts()) {
                is Success -> _profileUiState.value = profileUiState.value.copy(
                    state = State.SUCCESS,
                    profileConcepts = concepts.data
                )

                NetworkError, TokenExpired -> _profileUiState.value = profileUiState.value.copy(
                    state = State.NETWORK_ERROR
                )

                is Failure, is Unexpected -> _profileUiState.value = profileUiState.value.copy(
                    state = State.NONE
                )
            }
        }
    }

    private fun fetchUser(onFetched: (user: User?) -> Unit = {}) {
        viewModelScope.launch {
            _userUiState.value = userUiState.value.copy(state = State.LOADING)
            when (val user = userRepository.getUser()) {
                is Success -> {
                    _userUiState.emit(userUiState.value.copy(state = State.SUCCESS, user = user.data))
                    onFetched(user.data)
                }

                NetworkError, TokenExpired -> {
                    _userUiState.emit(userUiState.value.copy(state = State.NETWORK_ERROR))
                    onFetched(null)
                }

                is Failure, is Unexpected -> {
                    _userUiState.emit(userUiState.value.copy(state = State.NONE))
                    onFetched(null)
                }
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

    private fun fetchFeeds(userId: Long) {
        viewModelScope.launch {
            when (val feeds = petFeedRepository.getPetFeeds(userId)) {
                is Success -> _petFeedUiState.value = petFeedUiState.value.copy(petFeeds = feeds.data)
                NetworkError, TokenExpired -> _petFeedUiState.emit(petFeedUiState.value.copy(state = State.NETWORK_ERROR))
                is Failure, is Unexpected -> _petFeedUiState.value = petFeedUiState.value.copy(state = State.NONE)
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