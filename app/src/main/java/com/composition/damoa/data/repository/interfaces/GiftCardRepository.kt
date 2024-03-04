package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse

interface GiftCardRepository {
    suspend fun getGiftCards(): ApiResponse<List<GiftCard>>

    suspend fun useGiftCard(giftCode: String): ApiResponse<Unit>
}