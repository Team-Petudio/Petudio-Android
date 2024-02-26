package com.composition.damoa.presentation.screens.home.screen.gallery.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.ui.theme.Gray30
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalFoundationApi::class)
fun GalleryTabRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    tabTitlesRes: ImmutableList<Int>,
) {
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        modifier = modifier.wrapContentSize(),
        backgroundColor = Color.White,
        selectedTabIndex = pagerState.currentPage,
        divider = { },
        indicator = { },
        edgePadding = 0.dp,
    ) {
        tabTitlesRes.forEachIndexed { index, titleRes ->
            GalleryTabItem(
                tabTitle = stringResource(titleRes),
                isSelected = pagerState.currentPage == index,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } }
            )
        }
    }
}

@Composable
private fun GalleryTabItem(
    tabTitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val tabFontColor = if (isSelected) Color.Black else Gray30

    Tab(
        modifier = Modifier.wrapContentSize(),
        text = { MediumTitle(title = tabTitle, fontColor = tabFontColor) },
        selected = isSelected,
        onClick = onClick,
    )
}
