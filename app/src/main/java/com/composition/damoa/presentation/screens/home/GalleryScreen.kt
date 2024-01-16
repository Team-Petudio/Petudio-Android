package com.composition.damoa.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.ui.theme.Gray30
import com.composition.damoa.presentation.ui.theme.PrimaryColors
import com.composition.damoa.presentation.ui.theme.Purple60
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class Album(
    val id: Int,
    val title: String,
    val thumbnailUrl: String,
    val photoUrls: List<String>,
)

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun GalleryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()

    Column(
        modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        val tabTitlesRes = listOf(R.string.album, R.string.feed)
        GalleryTabRow(pagerState = pagerState, tabTitlesRes = tabTitlesRes, scope = scope)
        ContentScreen(pagerState = pagerState, navController = navController)
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
) {
    HorizontalPager(modifier = modifier.fillMaxSize(), state = pagerState) { page ->
        when (page) {
            0 ->
                AlbumScreen(
                    onAiProfileClick = {
                        navController.navigate(HomeBottomNavItem.AiProfile.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                )

            1 -> FeedScreen()
        }
    }
}

@Composable
private fun AlbumScreen(
    modifier: Modifier = Modifier,
    albums: List<Album> = emptyList(),
    onAiProfileClick: () -> Unit,
) {
    Surface(
        modifier =
            Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
    ) {
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
                Modifier
                    .fillMaxWidth(0.9F)
                    .aspectRatio(5 / 1F),
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
private fun FeedScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier
            .background(Color.Green)
            .fillMaxSize(),
    ) {
    }
}

@Preview
@Composable
private fun GalleryScreenPreview() {
    GalleryScreen(navController = rememberNavController())
}
