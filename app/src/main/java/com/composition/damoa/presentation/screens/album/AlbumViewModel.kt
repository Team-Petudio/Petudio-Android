package com.composition.damoa.presentation.screens.album

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.TokenExpired
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.repository.interfaces.AlbumRepository
import com.composition.damoa.presentation.common.base.BaseUiState.State
import com.composition.damoa.presentation.common.utils.imageSaver.ImageSaver
import com.composition.damoa.presentation.screens.album.state.AlbumUiEvent
import com.composition.damoa.presentation.screens.album.state.AlbumUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val imageSaver: ImageSaver,
    private val albumRepository: AlbumRepository,
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        viewModelScope.launch {
            _albumUiState.emit(albumUiState.value.copy(state = State.NONE))
            _event.emit(AlbumUiEvent.SAVE_PHOTOS_FAILURE)
        }
    }

    private val _albumUiState = MutableStateFlow(
        AlbumUiState(
            album = AlbumUiState.dummy,
            onSavePhotosClick = ::saveAllPhotos,
        )
    )
    val albumUiState = _albumUiState.asStateFlow()

    private val _event = MutableSharedFlow<AlbumUiEvent>()
    val event = _event.asSharedFlow()


    init {
        val albumId = savedStateHandle.get<Long>(KEY_ALBUM_ID) ?: INVALID_ALBUM_ID
        fetchAlbum(albumId)
    }

    private fun fetchAlbum(id: Long) {
        viewModelScope.launch {
            _albumUiState.emit(albumUiState.value.copy(state = State.LOADING))

            when (val result = albumRepository.getAlbum(id)) {
                is Success -> _albumUiState.emit(albumUiState.value.copy(album = result.data, state = State.SUCCESS))
                is Failure, is Unexpected -> _albumUiState.emit(albumUiState.value.copy(state = State.NONE))
                NetworkError, TokenExpired -> {
                    _albumUiState.emit(albumUiState.value.copy(state = State.NETWORK_ERROR))
                    _event.emit(AlbumUiEvent.NETWORK_ERROR)
                }
            }
        }
    }

    private fun saveAllPhotos() {
        val photoUrls = albumUiState.value.album.photoUrls

        viewModelScope.launch(exceptionHandler) {
            _albumUiState.emit(albumUiState.value.copy(state = State.LOADING))

            photoUrls.map { photoUrl ->
                launch { imageSaver.saveImageFromUrl(photoUrl) }
            }.joinAll()

            _albumUiState.emit(albumUiState.value.copy(state = State.NONE))
            _event.emit(AlbumUiEvent.SAVE_PHOTOS_SUCCESS)
        }
    }

    companion object {
        const val KEY_ALBUM_ID = "albumId"
        private const val INVALID_ALBUM_ID = -1L
    }
}