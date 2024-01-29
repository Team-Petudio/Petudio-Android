package com.composition.damoa.presentation.common.base

abstract class BaseUiState {
    abstract val state: State

    enum class State {
        NONE,
        LOADING,
        NETWORK_ERROR,
        SUCCESS,
    }
}