package com.composition.damoa.presentation.screens.profileCreation.state

import com.composition.damoa.data.model.ProfileConceptDetail
import com.composition.damoa.presentation.common.base.BaseUiState

data class ProfileConceptUiState(
    override val state: State = State.NONE,
    val profileConceptDetail: ProfileConceptDetail = ProfileConceptDetail(),
) : BaseUiState() {
}