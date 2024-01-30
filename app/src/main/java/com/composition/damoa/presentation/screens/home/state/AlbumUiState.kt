package com.composition.damoa.presentation.screens.home.state

import com.composition.damoa.data.model.Album
import com.composition.damoa.presentation.common.base.BaseUiState
import java.time.LocalDateTime

data class AlbumUiState(
    override val state: State = State.NONE,
    val albums: List<Album> = emptyList(),
) : BaseUiState() {
    companion object {
        val dummy = AlbumUiState(
            state = State.SUCCESS,
            albums = listOf(
                Album(
                    id = 0,
                    title = "코코",
                    concept = "트렌디 룩북 컨셉",
                    thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    photoUrls =
                    listOf(
                        "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                        "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                        "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    ),
                    date = LocalDateTime.now(),
                ),
                Album(
                    id = 1,
                    title = "루다",
                    concept = "트렌디 룩북 컨셉",
                    thumbnailUrl = "https://mblogthumb-phinf.pstatic.net/MjAxODA3MTBfMTY4/MDAxNTMxMjAyODE5MDc2.kVMC7FdEN76iOiSRi672EUoT9bDm6WJnHn0YFIaglo8g.uAQXzhnbWUkd30hXVCQdGhga_J3hJgXdshwo4dM-Awog.JPEG.pp0_0/IMG_0475.jpg?type=w800",
                    photoUrls =
                    listOf(
                        "https://mblogthumb-phinf.pstatic.net/MjAxODA3MTBfMTY4/MDAxNTMxMjAyODE5MDc2.kVMC7FdEN76iOiSRi672EUoT9bDm6WJnHn0YFIaglo8g.uAQXzhnbWUkd30hXVCQdGhga_J3hJgXdshwo4dM-Awog.JPEG.pp0_0/IMG_0475.jpg?type=w800",
                        "https://mblogthumb-phinf.pstatic.net/MjAxODA3MTBfMTY4/MDAxNTMxMjAyODE5MDc2.kVMC7FdEN76iOiSRi672EUoT9bDm6WJnHn0YFIaglo8g.uAQXzhnbWUkd30hXVCQdGhga_J3hJgXdshwo4dM-Awog.JPEG.pp0_0/IMG_0475.jpg?type=w800",
                        "https://mblogthumb-phinf.pstatic.net/MjAxODA3MTBfMTY4/MDAxNTMxMjAyODE5MDc2.kVMC7FdEN76iOiSRi672EUoT9bDm6WJnHn0YFIaglo8g.uAQXzhnbWUkd30hXVCQdGhga_J3hJgXdshwo4dM-Awog.JPEG.pp0_0/IMG_0475.jpg?type=w800",
                    ),
                    date = LocalDateTime.now(),
                ),
            ),

            )
    }
}
