package com.composition.damoa.presentation.screens.profileCreation.screen.creationIntroduce.state

import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.data.model.ProfileConceptDetail
import com.composition.damoa.presentation.common.base.BaseUiState

data class ProfileCreationIntroduceUiState(
    override val state: State = State.NONE,
    val conceptDetail: ProfileConceptDetail = ProfileConceptDetail(),
    val profileConcept: ProfileConcept? = null,
) : BaseUiState()