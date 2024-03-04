package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.mapper.toDomain
import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.network.service.GiftCardService
import com.composition.damoa.data.repository.interfaces.GiftCardRepository

class DefaultGiftCardRepository(
    private val service: GiftCardService,
) : GiftCardRepository {

    override suspend fun getGiftCards(): ApiResponse<List<GiftCard>> = service
        .getGiftCards()
        .map { it.data.toDomain() }

    override suspend fun useGiftCard(
        giftCode: String,
    ): ApiResponse<Unit> = service.useGiftCard(giftCode)
}