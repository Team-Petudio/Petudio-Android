package com.composition.damoa.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.TokenExpired
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.model.PetFeed
import com.composition.damoa.data.model.User
import com.composition.damoa.data.repository.interfaces.AlbumRepository
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.repository.interfaces.PetFeedRepository
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.presentation.common.base.BaseUiState.State
import com.composition.damoa.presentation.screens.home.screen.gallery.state.AlbumUiState
import com.composition.damoa.presentation.screens.home.screen.gallery.state.PetFeedUiState
import com.composition.damoa.presentation.screens.home.screen.profileConcept.state.ProfileConceptUiState
import com.composition.damoa.presentation.screens.home.screen.user.state.UserUiState
import com.composition.damoa.presentation.screens.home.screen.user.state.UserUiState.Companion.INVALID_USER_ID
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent.LOGOUT_FAILURE
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent.LOGOUT_SUCCESS
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent.NEED_LOGIN
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent.SIGN_OUT_FAILURE
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent.SIGN_OUT_SUCCESS
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent.TOKEN_EXPIRED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val conceptRepository: ConceptRepository,
    private val petFeedRepository: PetFeedRepository,
    private val albumRepository: AlbumRepository,
) : ViewModel() {
    private val _profileConceptUiState = MutableStateFlow(ProfileConceptUiState())
    val profileConceptUiState = _profileConceptUiState.asStateFlow()

    private val _albumUiState = MutableStateFlow(AlbumUiState())
    val albumUiState = _albumUiState.asStateFlow()

    private val _petFeedUiState = MutableStateFlow(
        PetFeedUiState(onLikeClick = ::toggleFeedLike)
    )
    val petFeedUiState = _petFeedUiState.asStateFlow()

    private val _userUiState = MutableStateFlow(
        UserUiState(
            onLogin = ::login,
            onLogout = ::logout,
            onSignOut = ::signOut,
        )
    )
    val userUiState = _userUiState.asStateFlow()

    private val _event = MutableSharedFlow<HomeUiEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchProfileConcepts()
        fetchAlbum()
        fetchUser { user -> fetchFeeds(user?.id ?: INVALID_USER_ID) }
    }

    private fun fetchProfileConcepts() {
        viewModelScope.launch {
            _profileConceptUiState.update { it.copy(state = State.LOADING) }
            when (val concepts = conceptRepository.getProfileConcepts()) {
                is Success -> _profileConceptUiState.update {
                    it.copy(state = State.SUCCESS, profileConcepts = concepts.data)
                }

                NetworkError, TokenExpired -> _profileConceptUiState.update { it.copy(state = State.NETWORK_ERROR) }
                is Failure, is Unexpected -> _profileConceptUiState.update { it.copy(state = State.NONE) }
            }
        }
    }

    private fun fetchAlbum() {
        viewModelScope.launch {
            when (val album = albumRepository.getAlbums()) {
                is Success -> _albumUiState.update { it.copy(state = State.SUCCESS, albums = album.data) }

                NetworkError, TokenExpired -> _albumUiState.update { it.copy(state = State.NETWORK_ERROR) }
                is Failure, is Unexpected -> _albumUiState.update { it.copy(state = State.NONE) }
            }
        }
    }

    private fun fetchFeeds(userId: Long) {
        viewModelScope.launch {
            when (val feeds = petFeedRepository.getPetFeeds(userId)) {
                is Success -> _petFeedUiState.update { it.copy(petFeeds = feeds.data) }
                NetworkError, TokenExpired -> _petFeedUiState.update { it.copy(state = State.NETWORK_ERROR) }
                is Failure, is Unexpected -> _petFeedUiState.update { it.copy(state = State.NONE) }
            }

        }
    }

    private fun toggleFeedLike(petFeedToToggle: PetFeed) {
        viewModelScope.launch {
            if (!userUiState.value.isLogined) {
                _event.emit(TOKEN_EXPIRED)
                return@launch
            }

            when (petFeedRepository.toggleLike(petFeedToToggle.id)) {
                is Success -> _petFeedUiState.update {
                    it.copy(petFeeds = it.petFeeds.updateFeedLike(petFeedToToggle.id))
                }

                NetworkError, TokenExpired -> _petFeedUiState.update { it.copy(state = State.NETWORK_ERROR) }
                is Failure, is Unexpected -> _petFeedUiState.update { it.copy(state = State.NONE) }
            }
        }
    }

    private fun List<PetFeed>.updateFeedLike(feedIdToToggle: Long): List<PetFeed> {
        return this.map { petFeed ->
            if (petFeed.id == feedIdToToggle) updatedFeedLike(petFeed) else petFeed
        }
    }

    private fun updatedFeedLike(petFeed: PetFeed) = petFeed.copy(
        isLike = !petFeed.isLike,
        likeCount = if (petFeed.isLike) petFeed.likeCount - 1 else petFeed.likeCount + 1
    )

    private fun fetchUser(onFetched: (user: User?) -> Unit = {}) {
        viewModelScope.launch {
            _userUiState.update { it.copy(state = State.LOADING) }
            when (val user = userRepository.getUser()) {
                is Success -> {
                    _userUiState.update { it.copy(state = State.SUCCESS, user = user.data) }
                    onFetched(user.data)
                }

                NetworkError, TokenExpired -> {
                    _userUiState.update { it.copy(state = State.NETWORK_ERROR) }
                    onFetched(null)
                }

                is Failure, is Unexpected -> {
                    _userUiState.update { it.copy(state = State.NONE) }
                    onFetched(null)
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch { _event.emit(NEED_LOGIN) }
    }

    private fun logout() {
        viewModelScope.launch {
            when (userRepository.logout()) {
                is Success -> _event.emit(LOGOUT_SUCCESS)
                else -> _event.emit(LOGOUT_FAILURE)
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            when (userRepository.signOut()) {
                is Success -> _event.emit(SIGN_OUT_SUCCESS)
                else -> _event.emit(SIGN_OUT_FAILURE)
            }
        }
    }
}