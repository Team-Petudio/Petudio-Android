package com.composition.damoa.presentation.common.base

abstract class BaseUiState {
    abstract val state: State
    val isLogined by lazy { state == State.SUCCESS }

    enum class State {
        NONE,
        LOADING,
        NETWORK_ERROR,
        SUCCESS,
    }
}