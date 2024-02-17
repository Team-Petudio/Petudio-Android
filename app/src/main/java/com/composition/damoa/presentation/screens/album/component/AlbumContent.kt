package com.composition.damoa.presentation.screens.album.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.data.model.Album
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.MediumDescription

@Composable
fun AlbumContent(
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

