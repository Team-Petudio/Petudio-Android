package com.composition.damoa.presentation.screens.home

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.R
import com.composition.damoa.data.model.Album
import com.composition.damoa.data.model.PetFeed
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.components.LoginButton
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.screens.album.AlbumActivity
import com.composition.damoa.presentation.screens.home.state.AlbumUiState
import com.composition.damoa.presentation.screens.home.state.PetFeedUiState
import com.composition.damoa.presentation.screens.profileCreation.ProfileCreationActivity
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Gray30
import com.composition.damoa.presentation.ui.theme.Gray40
import com.composition.damoa.presentation.ui.theme.Gray60
import com.composition.damoa.presentation.ui.theme.PrimaryColors
import com.composition.damoa.presentation.ui.theme.Purple60
import com.composition.damoa.presentation.ui.theme.Purple80
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val ALBUM_PAGE = 0
private const val PET_FEED_PAGE = 1

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun GalleryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    pagerState: PagerState = rememberPagerState { 2 },
    scope: CoroutineScope = rememberCoroutineScope(),
    albumUiState: AlbumUiState,
    petFeedUiState: PetFeedUiState,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        val tabTitlesRes = listOf(R.string.album, R.string.pet_feed)
        GalleryTabRow(pagerState = pagerState, tabTitlesRes = tabTitlesRes, scope = scope)
        ContentScreen(
            pagerState = pagerState,
            navController = navController,
            albumUiState = albumUiState,
            petFeedUiState = petFeedUiState,
            onLoginClick = onLoginClick,
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun GalleryTabRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    tabTitlesRes: List<Int>,
    scope: CoroutineScope,
) {
    ScrollableTabRow(
        modifier = modifier.wrapContentSize(),
        backgroundColor = Color.White,
        selectedTabIndex = pagerState.currentPage,
        divider = { },
        indicator = { },
        edgePadding = 0.dp,
    ) {
        tabTitlesRes.forEachIndexed { index, titleRes ->
            val tabTitle = stringResource(id = titleRes)
            GalleryTab(tabTitle, pagerState, index, scope)
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun GalleryTab(
    tabTitle: String,
    pagerState: PagerState,
    index: Int,
    scope: CoroutineScope,
) {
    val isSelected = pagerState.currentPage == index
    val tabFontColor = if (isSelected) Color.Black else Gray30
    Tab(
        modifier = Modifier.wrapContentSize(),
        text = { MediumTitle(title = tabTitle, fontColor = tabFontColor) },
        selected = isSelected,
        onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ContentScreen(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    navController: NavController,
    albumUiState: AlbumUiState,
    petFeedUiState: PetFeedUiState,
    onLoginClick: () -> Unit,
) {

    HorizontalPager(
        modifier = modifier.fillMaxSize(),
        state = pagerState,
    ) { page ->
        when (page) {
            ALBUM_PAGE -> AlbumScreen(
                albumUiState = albumUiState,
                onAiProfileClick = { navigateToProfileConceptScreen(navController = navController) },
                onLoginClick = onLoginClick,
            )

            PET_FEED_PAGE -> PetFeedScreen(
                petFeeds = petFeedUiState.petFeeds,
                navController = navController,
            )
        }
    }
}

@Composable
private fun LoginRequireScreen(
    onLoginClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        MediumTitle(
            fontColor = Purple80,
            titleRes = R.string.login_required_message,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 48.dp),
        )
        LoginButton(
            modifier = Modifier.fillMaxWidth(0.8F),
            onClick = onLoginClick,
        )
    }
}

@Composable
private fun AlbumScreen(
    modifier: Modifier = Modifier,
    albumUiState: AlbumUiState,
    onAiProfileClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    val albums = albumUiState.albums

    Surface(modifier = modifier.padding(horizontal = 20.dp)) {
        when {
            !albumUiState.isLogined -> LoginRequireScreen(onLoginClick = onLoginClick)
            albums.isEmpty() -> EmptyAlbumScreen(modifier, onAiProfileClick)
            else -> AlbumListScreen(modifier, albums)
        }
    }
}

@Composable
private fun EmptyAlbumScreen(
    modifier: Modifier = Modifier,
    onAiProfileClick: () -> Unit,
) {
    Column(
        modifier.padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        MediumTitle(
            fontColor = Purple60,
            titleRes = R.string.empty_album_content,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 40.dp),
        )
        GradientButton(
            modifier =
            Modifier.aspectRatio(5 / 1F),
            text = stringResource(id = R.string.create_ai_profile_button_content),
            gradient = Brush.linearGradient(PrimaryColors),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            onClick = onAiProfileClick,
        )
    }
}

@Composable
private fun AlbumListScreen(
    modifier: Modifier = Modifier,
    albums: List<Album>,
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(28.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
    ) {
        items(albums) { album ->
            AlbumItem(album = album) {
                context.startActivity(Intent(context, AlbumActivity::class.java))
            }
        }
    }
}

@Composable
private fun AlbumItem(
    modifier: Modifier = Modifier,
    album: Album,
    onClick: () -> Unit = {},
) {
    Column(
        modifier =
        modifier
            .border(BorderStroke(3.dp, Brush.verticalGradient(PrimaryColors)), RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
    ) {
        AlbumThumbnailImage(thumbnailUrl = album.thumbnailUrl)
        AlbumBody(modifier = Modifier.padding(bottom = 12.dp), album = album)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun AlbumThumbnailImage(
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
        modifier =
        modifier
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

@Composable
private fun PetFeedScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    petFeeds: List<PetFeed> = emptyList(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
    ) {
        items(petFeeds) { petFeed ->
            PetFeedItem(petFeed = petFeed) {
                navigateToProfileCreation(context)
                scope.launch {
                    delay(500L)
                    navigateToProfileConceptScreen(navController)
                }
            }
        }
    }
}

@Composable
private fun PetFeedItem(
    modifier: Modifier = Modifier,
    petFeed: PetFeed,
    onClick: () -> Unit = {},
) {
    Column(modifier) {
        PetThumbnailImage(thumbnailUrl = petFeed.thumbnailUrl)
        FeedBody(petFeed = petFeed)
        ConceptButton(modifier = Modifier.padding(top = 20.dp), onClick = onClick)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun PetThumbnailImage(
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
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
    ) {
        FeedTitle(petFeed)
        LikeButton(petFeed = petFeed)
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
    onLikeChanged: (Boolean) -> Unit = {},
) {
    IconToggleButton(
        checked = petFeed.isLike,
        onCheckedChange = onLikeChanged,
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
    if (number < 1000) return number.toString()
    if (number in 1000 until 1100) return "1K"
    return String.format("%.1fK", number / 1000.0)
}

@Composable
private fun ConceptButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
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
            Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null, tint = Gray60)
        }
    }
}

private fun navigateToProfileConceptScreen(navController: NavController) {
    navController.navigate(HomeBottomNavItem.ProfileConcept.route) {
        popUpTo(navController.graph.startDestinationId)
        launchSingleTop = true
    }
}

private fun navigateToProfileCreation(context: Context) {
    context.startActivity(Intent(context, ProfileCreationActivity::class.java))
}

@Preview
@Composable
private fun AlbumItemPreview() {
    AlbumItem(
        album =
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
    )
}

@Preview
@Composable
private fun PetFeedItemPreview() {
    PetFeedItem(
        petFeed =
        PetFeed(
            id = 0,
            title = "코코",
            concept = "트렌디 룩북 컨셉",
            isLike = false,
            thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
            likeCount = 0,
        ),
    )
}
