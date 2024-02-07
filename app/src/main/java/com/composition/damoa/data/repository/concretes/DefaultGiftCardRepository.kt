package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.mapper.toDomain
import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.data.repository.interfaces.GiftCardRepository
import com.composition.damoa.data.service.GiftCardService

class DefaultGiftCardRepository(
    private val service: GiftCardService,
) : GiftCardRepository {

    override suspend fun getGiftCards(): ApiResponse<List<GiftCard>> = service
        .getGiftCards()
        .map { it.data.toDomain() }
}