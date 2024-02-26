package com.composition.damoa.presentation.screens.home.screen.gallery

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.composition.damoa.R
import com.composition.damoa.presentation.common.extensions.navigateSingleTop
import com.composition.damoa.presentation.screens.album.AlbumActivity
import com.composition.damoa.presentation.screens.home.component.HomeBottomNavItem
import com.composition.damoa.presentation.screens.home.screen.gallery.component.AlbumScreen
import com.composition.damoa.presentation.screens.home.screen.gallery.component.GalleryTabRow
import com.composition.damoa.presentation.screens.home.screen.gallery.component.PetFeedScreen
import com.composition.damoa.presentation.screens.home.screen.gallery.state.AlbumUiState
import com.composition.damoa.presentation.screens.home.screen.gallery.state.PetFeedUiState
import com.composition.damoa.presentation.screens.profileCreation.ProfileCreationActivity
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


enum class GalleryPage {
    ALBUM,
    PET_FEED;

    companion object {
        private const val ALBUM_PAGE = 0
        private const val PET_FEED_PAGE = 1

        fun of(page: Int): GalleryPage = when (page) {
            ALBUM_PAGE -> ALBUM
            PET_FEED_PAGE -> PET_FEED
            else -> throw IllegalArgumentException("[ERROR]: 올바르지 않은 페이지입니다. -> $page")
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun GalleryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    pagerState: PagerState = rememberPagerState { 2 },
    albumUiState: AlbumUiState,
    petFeedUiState: PetFeedUiState,
    onLoginClick: () -> Unit,
) {
    val tabTitlesRes = persistentListOf(R.string.album, R.string.pet_feed)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        GalleryTabRow(
            pagerState = pagerState,
            tabTitlesRes = tabTitlesRes
        )
        GalleryPager(
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
private fun GalleryPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    navController: NavController,
    albumUiState: AlbumUiState,
    petFeedUiState: PetFeedUiState,
    onLoginClick: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    HorizontalPager(
        modifier = modifier.fillMaxSize(),
        state = pagerState,
    ) { page ->
        when (GalleryPage.of(page)) {
            GalleryPage.ALBUM -> AlbumScreen(
                albums = albumUiState.albums,
                isLogined = albumUiState.isLogined,
                onAiProfileClick = { navController.navigateToProfileConceptScreen() },
                onLoginClick = onLoginClick,
                onAlbumClick = { albumId -> AlbumActivity.startActivity(context, albumId) },
            )

            GalleryPage.PET_FEED -> PetFeedScreen(
                petFeeds = petFeedUiState.petFeeds,
                onLikeClick = { petFeed -> petFeedUiState.onLikeClick(petFeed) },
                onProfileCreationClick = { petFeedId ->
                    navigateToProfileCreation(context = context, conceptId = petFeedId)
                    scope.launch {
                        delay(500L)
                        navController.navigateToProfileConceptScreen()
                    }
                },
            )
        }
    }
}

private fun NavController.navigateToProfileConceptScreen() {
    navigateSingleTop(HomeBottomNavItem.ProfileConcept.route)
}

private fun navigateToProfileCreation(context: Context, conceptId: Long) {
    ProfileCreationActivity.startActivity(context, conceptId)
}