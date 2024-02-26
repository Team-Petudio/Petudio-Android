package com.composition.damoa.presentation.screens.home.screen.gallery.state

import com.composition.damoa.data.model.Album
import com.composition.damoa.presentation.common.base.BaseUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class AlbumUiState(
    override val state: State = State.NONE,
    val albums: PersistentList<Album> = persistentListOf(),
) : BaseUiState()
