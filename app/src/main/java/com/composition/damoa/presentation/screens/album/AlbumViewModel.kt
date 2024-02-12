package com.composition.damoa.presentation.screens.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val imageSaver: ImageSaver,
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
}