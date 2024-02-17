package com.composition.damoa.presentation.screens.album

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.composition.damoa.R
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.common.extensions.requestWriteExternalStoragePermission
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.screens.album.component.AlbumScreen
import com.composition.damoa.presentation.screens.album.state.AlbumUiEvent
import com.composition.damoa.presentation.screens.album.state.AlbumUiEvent.NETWORK_ERROR
import com.composition.damoa.presentation.screens.album.state.AlbumUiEvent.SAVE_PHOTOS_FAILURE
import com.composition.damoa.presentation.screens.album.state.AlbumUiEvent.SAVE_PHOTOS_SUCCESS
import com.composition.damoa.presentation.screens.album.state.AlbumUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AlbumActivity : ComponentActivity() {
    private val viewModel: AlbumViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlbumScreen(
                viewModel = viewModel,
                onSaveClick = { saveAllPhotos(viewModel.albumUiState.value) }
            )
        }

        viewModel.event.collectEvent()
    }

    private fun SharedFlow<AlbumUiEvent>.collectEvent() {
        onUi {
            collectLatest { event ->
                when (event) {
                    SAVE_PHOTOS_SUCCESS -> showToast(R.string.photos_save_success_message)
                    SAVE_PHOTOS_FAILURE -> showToast(R.string.photos_save_failure_message)
                    NETWORK_ERROR -> showToast(R.string.network_failure_message)
                }
            }
        }
    }

    private fun saveAllPhotos(uiState: AlbumUiState) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            uiState.onSaveAllPhotosClick()
            return
        }

        requestWriteExternalStoragePermission(
            message = getString(R.string.permission_request_photos_save_permission_message),
            onGranted = { uiState.onSaveAllPhotosClick() },
            onDenied = { showToast(R.string.photos_save_permission_denied_message) },
        )
    }

    companion object {
        fun startActivity(context: Context, albumId: Long) {
            val intent = Intent(context, AlbumActivity::class.java)
                .putExtra(AlbumViewModel.KEY_ALBUM_ID, albumId)
            context.startActivity(intent)
        }
    }
}
