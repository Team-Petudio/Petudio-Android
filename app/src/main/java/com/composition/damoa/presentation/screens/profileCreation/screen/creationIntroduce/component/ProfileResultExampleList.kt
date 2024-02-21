package com.composition.damoa.presentation.screens.profileCreation.screen.creationIntroduce.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ProfileResultExampleList(
    modifier: Modifier = Modifier,
    photoUrls: List<String>,
) {
    LazyVerticalGrid(
        modifier = modifier
            .background(Color.White)
            .heightIn(max = 1000.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 20.dp),
    ) {
        items(photoUrls) { photoUrl ->
            ProfileResultExampleItem(photoUrl = photoUrl)
        }
    }
}