package com.composition.damoa.presentation.screens.profileCreation.state

import com.composition.damoa.presentation.common.base.BaseUiState

data class PointUiState(
    override val state: State = State.NONE,
    val point: Int = 0,
) : BaseUiState()