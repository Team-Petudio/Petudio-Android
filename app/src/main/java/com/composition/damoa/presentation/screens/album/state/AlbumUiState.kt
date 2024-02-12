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
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
                "https://www.shutterstock.com/image-illustration/pristine-reflective-lake-show-image-260nw-2305485315.jpg",
                "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_1280.jpg",
                "https://dfstudio-d420.kxcdn.com/wordpress/wp-content/uploads/2019/06/digital_camera_photo-1080x675.jpg",
                "https://imgupscaler.com/images/samples/animal-after.webp",
                "https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510_640.jpg",
                "https://slp-statics.astockcdn.net/static_assets/staging/24winter/home/curated-collections/card-2.jpg",
            ),
            date = LocalDateTime.now(),
        )
    }
}