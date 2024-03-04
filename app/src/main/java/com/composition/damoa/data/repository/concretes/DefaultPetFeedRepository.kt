package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.mapper.toDomain
import com.composition.damoa.data.model.PetFeed
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.network.service.PetFeedService
import com.composition.damoa.data.repository.interfaces.PetFeedRepository

class DefaultPetFeedRepository(
    private val service: PetFeedService,
) : PetFeedRepository {

    override suspend fun getPetFeeds(
        userId: Long,
    ): ApiResponse<List<PetFeed>> = service
        .getPetFeeds(userId)
        .map { it.data.toDomain() }

    override suspend fun toggleLike(
        petFeedId: Long,
    ): ApiResponse<Unit> = service.toggleLike(petFeedId)
}