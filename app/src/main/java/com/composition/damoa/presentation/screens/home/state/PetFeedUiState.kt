package com.composition.damoa.presentation.screens.home.state

import com.composition.damoa.data.model.PetFeed
import com.composition.damoa.presentation.common.base.BaseUiState

data class PetFeedUiState(
    override val state: State = State.NONE,
    val petFeeds: List<PetFeed> = emptyList(),
) : BaseUiState() {
    companion object {
        val dummy = PetFeedUiState(
            state = State.SUCCESS,
            petFeeds = listOf(
                PetFeed(
                    id = 0,
                    title = "코코",
                    concept = "트렌디 룩북 컨셉",
                    isLike = true,
                    thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    likeCount = 12300,
                ),
                PetFeed(
                    id = 0,
                    title = "코코",
                    concept = "트렌디 룩북 컨셉",
                    isLike = false,
                    thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    likeCount = 2122,
                ),
                PetFeed(
                    id = 0,
                    title = "코코",
                    concept = "트렌디 룩북 컨셉",
                    isLike = false,
                    thumbnailUrl = "https://img.freepik.com/premium-photo/picture-of-a-cute-puppy-world-animal-day_944128-5890.jpg",
                    likeCount = 0,
                ),
            ),

            )
    }
}

