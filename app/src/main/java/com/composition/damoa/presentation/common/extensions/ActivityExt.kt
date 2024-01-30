package com.composition.damoa.presentation.common.extensions

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ComponentActivity.repeatOnStarted(block: suspend () -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

fun ComponentActivity.onUi(block: suspend () -> Unit) {
    lifecycleScope.launch(Dispatchers.Main) { block() }
}
