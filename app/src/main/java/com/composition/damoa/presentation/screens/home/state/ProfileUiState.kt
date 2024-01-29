package com.composition.damoa.presentation.screens.home.state

import com.composition.damoa.presentation.common.base.BaseUiState
import com.composition.damoa.presentation.screens.home.ProfileConcept

data class ProfileUiState(
    override val state: State = State.NONE,
    val profileConcepts: List<ProfileConcept> = emptyList(),
) : BaseUiState()