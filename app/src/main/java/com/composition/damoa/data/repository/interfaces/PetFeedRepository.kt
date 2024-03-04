package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.model.PetFeed
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse

interface PetFeedRepository {
    suspend fun getPetFeeds(userId: Long): ApiResponse<List<PetFeed>>

    suspend fun toggleLike(petFeedId: Long): ApiResponse<Unit>
}