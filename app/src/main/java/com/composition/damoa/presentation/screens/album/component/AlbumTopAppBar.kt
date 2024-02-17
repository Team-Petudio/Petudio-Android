package com.composition.damoa.presentation.screens.album.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.NavigationButton
import com.composition.damoa.presentation.common.components.SmallTitle
import com.composition.damoa.presentation.ui.theme.Purple60
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AlbumTopAppBar(
    onNavigationClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    TopAppBar(
        title = { },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        navigationIcon = { NavigationButton(onNavigationClick) },
        actions = { SaveAllButton(onSaveClick) },
    )
}

@OptIn(FlowPreview::class)
@Composable
private fun SaveAllButton(onSaveClick: () -> Unit) {
    val scope = rememberCoroutineScope()
    val clickFlow = remember { MutableSharedFlow<Unit>() }

    LaunchedEffect(clickFlow) {
        clickFlow
            .sample(1000)
            .collect { onSaveClick() }
    }

    TextButton(
        onClick = { scope.launch { clickFlow.emit(Unit) } }
    ) {
        SmallTitle(titleRes = R.string.save_all, fontColor = Purple60)
    }
}