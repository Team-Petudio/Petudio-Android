package com.composition.damoa.presentation.screens.home.screen.gallery.state

import com.composition.damoa.data.model.Album
import com.composition.damoa.presentation.common.base.BaseUiState

data class AlbumUiState(
    override val state: State = State.NONE,
    val albums: List<Album> = emptyList(),
) : BaseUiState()
