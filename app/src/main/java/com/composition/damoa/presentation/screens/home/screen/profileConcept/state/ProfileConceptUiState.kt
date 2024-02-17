package com.composition.damoa.presentation.screens.home.screen.profileConcept.state

import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.presentation.common.base.BaseUiState

data class ProfileConceptUiState(
    override val state: State = State.NONE,
    val profileConcepts: List<ProfileConcept> = emptyList(),
) : BaseUiState()