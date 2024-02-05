package com.composition.damoa.presentation.common.extensions

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ComponentActivity.onUi(block: suspend CoroutineScope.() -> Unit): Job {
    return lifecycleScope.launch(Dispatchers.Main) { block() }
}

fun ComponentActivity.onDefault(block: suspend CoroutineScope.() -> Unit): Job {
    return lifecycleScope.launch(Dispatchers.Default) { block() }
}
