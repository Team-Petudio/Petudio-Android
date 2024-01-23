package com.composition.damoa.presentation.screens.login.state

import com.composition.damoa.presentation.common.base.BaseUiState

data class LoginUiState(
    override val state: State = State.NONE,
) : BaseUiState()