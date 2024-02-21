package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.PetFeed

interface PetFeedRepository {
    suspend fun getPetFeeds(userId: Long): ApiResponse<List<PetFeed>>

    suspend fun toggleLike(petFeedId: Long): ApiResponse<Unit>
}