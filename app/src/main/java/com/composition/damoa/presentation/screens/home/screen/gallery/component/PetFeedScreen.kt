package com.composition.damoa.presentation.screens.home.screen.gallery.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.R
import com.composition.damoa.data.model.PetFeed
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.common.components.ThrottledIconButton
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Gray60
import com.composition.damoa.presentation.ui.theme.Purple60


@Composable
fun PetFeedScreen(
    modifier: Modifier = Modifier,
    petFeeds: List<PetFeed> = emptyList(),
    onLikeClick: (PetFeed) -> Unit,
    onProfileCreationClick: (petFeedId: Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
    ) {
        items(
            items = petFeeds,
            key = { petFeed -> petFeed.id },
        ) { petFeed ->
            PetFeedItem(
                petFeed = petFeed,
                onLikeClick = { onLikeClick(petFeed) },
                onProfileCreationClick = { onProfileCreationClick(petFeed.id) },
            )
        }
    }
}

@Composable
private fun PetFeedItem(
    modifier: Modifier = Modifier,
    petFeed: PetFeed,
    onLikeClick: () -> Unit,
    onProfileCreationClick: () -> Unit = {},
) {
    Column(modifier) {
        FeedThumbnail(thumbnailUrl = petFeed.thumbnailUrl)
        FeedBody(petFeed = petFeed, onLikeClick = onLikeClick)
        ConceptButton(modifier = Modifier.padding(top = 20.dp), onClick = onProfileCreationClick)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun FeedThumbnail(
    modifier: Modifier = Modifier,
    thumbnailUrl: String,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
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
private fun FeedBody(
    modifier: Modifier = Modifier,
    petFeed: PetFeed,
    onLikeClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
    ) {
        FeedTitle(petFeed)
        LikeButton(petFeed = petFeed, onLikeClick = onLikeClick)
    }
}

@Composable
private fun FeedTitle(petFeed: PetFeed) {
    Column(modifier = Modifier.fillMaxWidth(0.8F)) {
        MediumTitle(title = petFeed.title)
        MediumDescription(
            modifier = Modifier.padding(top = 4.dp),
            description = petFeed.concept,
        )
    }
}

@Composable
private fun LikeButton(
    petFeed: PetFeed,
    onLikeClick: () -> Unit = {},
) {
    ThrottledIconButton(
        modifier = Modifier.padding(top = 4.dp),
        onClick = onLikeClick,
    ) {
        val likeCount = formatLikeCount(petFeed.likeCount)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (petFeed.isLike) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = null, tint = Purple60)
                Text(text = likeCount, fontSize = 14.sp, color = Purple60, fontWeight = FontWeight.Bold)
            } else {
                Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = null, tint = Color.Black)
                Text(text = likeCount, fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

private fun formatLikeCount(number: Int): String {
    if (number < 1_000) return number.toString()
    if (number in 1_000 until 1_100) return "1K"
    return String.format("%.1fK", number / 1_000.0)
}

@Composable
private fun ConceptButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    OutlinedButton(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Gray20),
        contentPadding = PaddingValues(vertical = 14.dp),
        enabled = enabled,
        onClick = onClick,
    ) {
        Row {
            MediumDescription(
                descriptionRes = R.string.create_profile_as_concept,
                fontColor = Gray60,
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                tint = Gray60,
                contentDescription = null,
            )
        }
    }
}
