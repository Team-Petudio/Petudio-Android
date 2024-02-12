package com.composition.damoa.presentation.screens.album.state

import com.composition.damoa.data.model.Album
import com.composition.damoa.presentation.common.base.BaseUiState
import java.time.LocalDateTime

data class AlbumUiState(
    override val state: State = State.NONE,
    val album: Album,
    val onSavePhotosClick: () -> Unit,
) : BaseUiState() {
    companion object {
        val dummy = Album(
            id = 0,
            title = "코코",
            concept = "트렌디 룩북 컨셉",
            thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
            photoUrls = listOf(
                "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
            ),
            date = LocalDateTime.now(),
        )
    }
}