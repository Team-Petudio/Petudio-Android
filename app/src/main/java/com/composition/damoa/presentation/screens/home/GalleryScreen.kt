package com.composition.damoa.presentation.screens.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.screens.profileCreation.ProfileCreationActivity
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Gray30
import com.composition.damoa.presentation.ui.theme.Gray60
import com.composition.damoa.presentation.ui.theme.PrimaryColors
import com.composition.damoa.presentation.ui.theme.Purple60
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Album(
    val id: Int,
    val title: String,
    val thumbnailUrl: String,
    val photoUrls: List<String>,
)

data class PetFeed(
    val id: Int,
    val title: String,
    val concept: String,
    val likeCount: Int,
    val isLike: Boolean,
    val thumbnailUrl: String,
)

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun GalleryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    pagerState: PagerState = rememberPagerState { 2 },
    scope: CoroutineScope = rememberCoroutineScope(),
    albums: List<Album> = emptyList(),
    petFeeds: List<PetFeed> = emptyList(),
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        val tabTitlesRes = listOf(R.string.album, R.string.pet_feed)
        GalleryTabRow(pagerState = pagerState, tabTitlesRes = tabTitlesRes, scope = scope)
        ContentScreen(pagerState = pagerState, navController = navController, albums = albums, petFeeds = petFeeds)
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
    albums: List<Album> = emptyList(),
    petFeeds: List<PetFeed> = emptyList(),
) {
    HorizontalPager(
        modifier = modifier.fillMaxSize(),
        state = pagerState,
    ) { page ->
        when (page) {
            0 ->
                AlbumScreen(
                    albums = albums,
                    onAiProfileClick = {
                        navigateToProfileConceptScreen(navController = navController)
                    },
                )

            1 ->
                PetFeedScreen(
                    petFeeds = petFeeds,
                    navController = navController,
                )
        }
    }
}

@Composable
private fun AlbumScreen(
    modifier: Modifier = Modifier,
    albums: List<Album> = emptyList(),
    onAiProfileClick: () -> Unit,
) {
    Surface(modifier = modifier.padding(horizontal = 20.dp)) {
        if (albums.isEmpty()) {
            EmptyAlbumScreen(modifier, onAiProfileClick)
        } else {
            AlbumListScreen(modifier, albums)
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
        contentPadding = PaddingValues(bottom = 20.dp),
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
    (context as Activity).startActivity(
        Intent(context, ProfileCreationActivity::class.java),
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

@Preview
@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun GalleryEmptyAlbumScreenPreview() {
    GalleryScreen(
        navController = rememberNavController(),
        pagerState = rememberPagerState(initialPage = 0) { 0 },
    )
}

@Preview
@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun GalleryAlbumScreenPreview() {
    GalleryScreen(
        navController = rememberNavController(),
        pagerState = rememberPagerState(initialPage = 0) { 0 },
    )
}

@Preview
@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun GalleryPetFeedScreenPreview() {
    GalleryScreen(
        navController = rememberNavController(),
        pagerState = rememberPagerState(initialPage = 1) { 0 },
        petFeeds =
            listOf(
                PetFeed(
                    id = 0,
                    title = "코코",
                    concept = "트렌디 룩북 컨셉",
                    isLike = false,
                    thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    likeCount = 0,
                ),
                PetFeed(
                    id = 0,
                    title = "코코",
                    concept = "트렌디 룩북 컨셉",
                    isLike = false,
                    thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    likeCount = 0,
                ),
                PetFeed(
                    id = 0,
                    title = "코코",
                    concept = "트렌디 룩북 컨셉",
                    isLike = false,
                    thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    likeCount = 0,
                ),
            ),
    )
}
