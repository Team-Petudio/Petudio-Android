package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.mapper.toDomain
import com.composition.damoa.data.model.PetFeed
import com.composition.damoa.data.repository.interfaces.PetFeedRepository
import com.composition.damoa.data.service.PetFeedService

class DefaultPetFeedRepository(
    private val service: PetFeedService,
) : PetFeedRepository {

    override suspend fun getPetFeeds(
        userId: Long,
    ): ApiResponse<List<PetFeed>> = service
        .getPetFeeds(userId)
        .map { it.data.toDomain() }
}