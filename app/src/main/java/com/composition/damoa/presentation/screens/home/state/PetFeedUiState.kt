package com.composition.damoa.presentation.screens.home.state

import com.composition.damoa.data.model.PetFeed
import com.composition.damoa.presentation.common.base.BaseUiState

data class PetFeedUiState(
    override val state: State = State.NONE,
    val petFeeds: List<PetFeed> = emptyList(),
) : BaseUiState()
