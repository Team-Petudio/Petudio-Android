package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.GiftCard

interface GiftCardRepository {
    suspend fun getGiftCards(): ApiResponse<List<GiftCard>>
}