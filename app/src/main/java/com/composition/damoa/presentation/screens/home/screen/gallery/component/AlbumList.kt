package com.composition.damoa.presentation.screens.home.screen.gallery.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.data.model.Album
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.ui.theme.Gray40
import com.composition.damoa.presentation.ui.theme.PrimaryColors
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun AlbumScreen(
    modifier: Modifier = Modifier,
    albums: List<Album>,
    isLogined: Boolean,
    onAiProfileClick: () -> Unit,
    onLoginClick: () -> Unit,
    onAlbumClick: (albumId: Long) -> Unit,
) {
    Surface(modifier = modifier.padding(horizontal = 20.dp)) {
        when {
            !isLogined -> LoginRequireGalleryScreen(onLoginClick = onLoginClick)
            albums.isEmpty() -> EmptyAlbumScreen(modifier = modifier, onAiProfileClick = onAiProfileClick)
            else -> AlbumList(modifier = modifier, albums = albums, onAlbumClick = onAlbumClick)
        }
    }
}

@Composable
private fun AlbumList(
    modifier: Modifier = Modifier,
    albums: List<Album>,
    onAlbumClick: (albumId: Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(28.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
    ) {
        items(
            items = albums,
            key = { album -> album.id },
        ) { album ->
            AlbumItem(album = album, onClick = onAlbumClick)
        }
    }
}

@Composable
private fun AlbumItem(
    modifier: Modifier = Modifier,
    album: Album,
    onClick: (albumId: Long) -> Unit,
) {
    Column(
        modifier = modifier
            .border(BorderStroke(3.dp, Brush.verticalGradient(PrimaryColors)), RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick(album.id) },
    ) {
        AlbumThumbnail(thumbnailUrl = album.thumbnailUrl)
        AlbumBody(modifier = Modifier.padding(bottom = 12.dp), album = album)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun AlbumThumbnail(
    modifier: Modifier = Modifier,
    thumbnailUrl: String,
) {
    Card(
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        elevation = 0.dp,
        modifier = modifier.aspectRatio(1F),
    ) {
        GlideImage(
            model = thumbnailUrl,
            contentDescription = null,
            transition = CrossFade,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun AlbumBody(
    modifier: Modifier = Modifier,
    album: Album,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .padding(top = 16.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth(),
    ) {
        AlbumTitle(album = album)
        AlbumDate(date = album.date)
    }
}

@Composable
private fun AlbumTitle(
    modifier: Modifier = Modifier,
    album: Album,
) {
    Column(modifier = modifier.fillMaxWidth(0.7F)) {
        MediumTitle(title = album.title)
        MediumDescription(
            modifier = Modifier.padding(top = 4.dp),
            description = album.concept,
        )
    }
}

@Composable
private fun AlbumDate(
    modifier: Modifier = Modifier,
    date: LocalDateTime,
) {
    val albumDate = date.format(DateTimeFormatter.ofPattern("yyyy. MM. dd"))

    MediumDescription(
        modifier = modifier.padding(top = 4.dp),
        description = albumDate,
        fontColor = Gray40,
    )
}