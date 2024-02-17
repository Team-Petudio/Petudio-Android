package com.composition.damoa.presentation.screens.album.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composition.damoa.presentation.common.base.BaseUiState
import com.composition.damoa.presentation.screens.album.AlbumViewModel
import com.composition.damoa.presentation.screens.album.state.AlbumUiState
import com.composition.damoa.presentation.ui.theme.PetudioTheme


@Composable
fun AlbumScreen(
    viewModel: AlbumViewModel,
    onSaveClick: () -> Unit,
) {
    PetudioTheme {
        val activity = LocalContext.current as ComponentActivity
        val albumUiState by viewModel.albumUiState.collectAsStateWithLifecycle()

        AlbumScreen(activity, albumUiState, onSaveClick)
    }
}

@Composable
private fun AlbumScreen(
    activity: ComponentActivity,
    uiState: AlbumUiState,
    onSaveClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            AlbumTopAppBar(
                onNavigationClick = { activity.finish() },
                onSaveClick = onSaveClick,
            )
        },
    ) { padding ->
        AlbumContent(
            modifier = Modifier
                .padding(top = padding.calculateTopPadding())
                .padding(horizontal = 20.dp),
            album = uiState.album,
        )

        if (uiState.state == BaseUiState.State.LOADING) SavePhotoLoading()
    }
}