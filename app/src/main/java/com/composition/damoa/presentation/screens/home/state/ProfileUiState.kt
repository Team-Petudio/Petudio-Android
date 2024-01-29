package com.composition.damoa.presentation.screens.home.state

import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.presentation.common.base.BaseUiState

data class ProfileUiState(
    override val state: State = State.NONE,
    val profileConcepts: List<ProfileConcept> = emptyList(),
) : BaseUiState()