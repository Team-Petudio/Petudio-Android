package com.composition.damoa.presentation.screens.album

import androidx.lifecycle.ViewModel
import com.composition.damoa.presentation.screens.album.state.AlbumUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(

) : ViewModel() {
    private val _albumUiState = MutableStateFlow(
        AlbumUiState(
            album = AlbumUiState.dummy,
            onSavePhotosClick = {},
        )
    )
    val albumUiState = _albumUiState.asStateFlow()

}