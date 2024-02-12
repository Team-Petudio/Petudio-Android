package com.composition.damoa.presentation.screens.album

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.R
import com.composition.damoa.data.model.Album
import com.composition.damoa.presentation.common.base.BaseUiState.State
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.CircularLoadingBar
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.SmallTitle
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.common.utils.permissionRequester.Permission
import com.composition.damoa.presentation.common.utils.permissionRequester.PermissionRequester
import com.composition.damoa.presentation.screens.album.state.AlbumUiEvent.SAVE_PHOTOS_FAILURE
import com.composition.damoa.presentation.screens.album.state.AlbumUiEvent.SAVE_PHOTOS_SUCCESS
import com.composition.damoa.presentation.screens.album.state.AlbumUiState
import com.composition.damoa.presentation.ui.theme.PetudioTheme
import com.composition.damoa.presentation.ui.theme.Purple60
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AlbumActivity : ComponentActivity() {
    private val viewModel: AlbumViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlbumScreen(viewModel = viewModel)
        }
    }
}

@Composable
private fun AlbumScreen(viewModel: AlbumViewModel) {
    PetudioTheme {
        val context = LocalContext.current
        val activity = context as? ComponentActivity
        val albumUiState by viewModel.albumUiState.collectAsStateWithLifecycle()

        activity?.onUi {
            viewModel.event.collectLatest { event ->
                when (event) {
                    SAVE_PHOTOS_SUCCESS -> context.showToast(R.string.photos_save_success_message)
                    SAVE_PHOTOS_FAILURE -> context.showToast(R.string.photos_save_failure_message)
                }
            }
        }

        Scaffold(
            topBar = {
                AlbumTopAppBar(
                    onNavigationClick = { activity?.finish() },
                    onSaveClick = { context.saveAllPhotos(albumUiState) },
                )
            },
        ) { padding ->
            AlbumContent(
                modifier = Modifier
                    .padding(top = padding.calculateTopPadding())
                    .padding(horizontal = 20.dp),
                album = albumUiState.album,
            )
            if (albumUiState.state == State.LOADING) LoadingScreen()
        }
    }
}

private fun Context.saveAllPhotos(
    albumUiState: AlbumUiState,
) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
        albumUiState.onSavePhotosClick()
        return
    }

    requestWriteExternalStoragePermission(
        onGranted = { albumUiState.onSavePhotosClick() },
        onDenied = { showToast(R.string.photos_save_permission_denied_message) },
    )
}

private fun Context.requestWriteExternalStoragePermission(
    onGranted: () -> Unit,
    onDenied: () -> Unit,
) {
    PermissionRequester().launch(
        context = this,
        permission = Permission.WRITE_EXTERNAL_STORAGE,
        dialogMessage = getString(R.string.permission_request_photos_save_permission_message),
        onGranted = onGranted,
        onDenied = onDenied,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AlbumTopAppBar(
    onNavigationClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    TopAppBar(
        title = { },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_back),
                    tint = Color.Black,
                )
            }
        },
        actions = { SaveAllButton(onSaveClick) },
    )
}

@Composable
private fun AlbumContent(
    modifier: Modifier = Modifier,
    album: Album,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 20.dp),
    ) {
        item(span = { GridItemSpan(2) }) { AlbumHeader(album) }
        items(
            items = album.photoUrls,
            key = { photoUrl -> photoUrl },
        ) { photoUrl ->
            PhotoItem(photoUrl = photoUrl)
        }
    }
}

@Composable
private fun AlbumHeader(album: Album) {
    Column {
        BigTitle(title = album.title, modifier = Modifier.padding(top = 20.dp))
        MediumDescription(description = album.concept, modifier = Modifier.padding(top = 12.dp))
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun PhotoItem(
    modifier: Modifier = Modifier,
    photoUrl: String,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp,
        modifier = modifier.aspectRatio(1F),
    ) {
        GlideImage(
            model = photoUrl,
            contentDescription = null,
            transition = CrossFade,
            contentScale = ContentScale.Crop,
        )
    }
}

@OptIn(FlowPreview::class)
@Composable
private fun SaveAllButton(onSaveClick: () -> Unit) {
    val scope = rememberCoroutineScope()
    val clickFlow = remember { MutableSharedFlow<Unit>() }

    LaunchedEffect(clickFlow) {
        clickFlow
            .sample(1000)
            .collect { onSaveClick() }
    }

    TextButton(onClick = { scope.launch { clickFlow.emit(Unit) } }) {
        SmallTitle(titleRes = R.string.save_all, fontColor = Purple60)
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {
        CircularLoadingBar(size = 70.dp)
    }
}