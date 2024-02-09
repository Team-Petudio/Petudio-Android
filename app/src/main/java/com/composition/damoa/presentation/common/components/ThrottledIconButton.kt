package com.composition.damoa.presentation.common.components

import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@Composable
fun ThrottledIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val clickFlow = remember { MutableSharedFlow<Unit>() }

    LaunchedEffect(clickFlow) {
        clickFlow
            .sample(1000) // 1초 동안 추가 클릭 무시
            .collect { onClick() }
    }

    IconButton(
        modifier = modifier,
        onClick = { scope.launch { clickFlow.emit(Unit) } },
        content = content
    )
}