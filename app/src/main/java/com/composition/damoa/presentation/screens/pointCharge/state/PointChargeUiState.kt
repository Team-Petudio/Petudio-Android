package com.composition.damoa.presentation.screens.pointCharge.state

import com.composition.damoa.presentation.common.base.BaseUiState

data class PointChargeUiState(
    override val state: State = State.NONE,
    val point: Int = 0,
) : BaseUiState()