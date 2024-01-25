package com.composition.damoa.presentation.common.extensions

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun Modifier.throttleClickable(
    throttleTime: Long = 100,
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier = composed {
    val lastClickTimestamp = remember { mutableLongStateOf(0L) }
    val coroutineScope = rememberCoroutineScope()

    this.clickable(
        interactionSource = interactionSource,
        indication = indication,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = {
            val currentTimestamp = System.currentTimeMillis()
            if (currentTimestamp - lastClickTimestamp.value >= throttleTime) {
                coroutineScope.launch {
                    withContext(Dispatchers.Main) {
                        onClick()
                    }
                }
                lastClickTimestamp.value = currentTimestamp
            }
        }
    )
}

fun Modifier.throttleToggleable(
    throttleTime: Long = 100,
    value: Boolean,
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean = true,
    role: Role? = null,
    onValueChange: (Boolean) -> Unit,
): Modifier = composed {
    val lastClickTimestamp = remember { mutableLongStateOf(0L) }
    val coroutineScope = rememberCoroutineScope()

    this.toggleable(
        value = value,
        interactionSource = interactionSource,
        indication = indication,
        enabled = enabled,
        role = role,
        onValueChange = {
            val currentTimestamp = System.currentTimeMillis()
            if (currentTimestamp - lastClickTimestamp.longValue >= throttleTime) {
                coroutineScope.launch {
                    withContext(Dispatchers.Main) {
                        onValueChange(it)
                    }
                }
                lastClickTimestamp.longValue = currentTimestamp
            }
        }
    )
}