package com.composition.damoa.presentation.screens.home.screen.profileConcept.state

import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.presentation.common.base.BaseUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ProfileConceptUiState(
    override val state: State = State.NONE,
    val profileConcepts: PersistentList<ProfileConcept> = persistentListOf(),
) : BaseUiState()