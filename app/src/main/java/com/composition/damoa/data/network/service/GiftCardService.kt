package com.composition.damoa.data.network.service

import com.composition.damoa.data.network.dto.response.BaseResponse
import com.composition.damoa.data.network.dto.response.GiftCardsResponse
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface GiftCardService {
    @GET("/api/v1/gifts")
    suspend fun getGiftCards(): ApiResponse<BaseResponse<GiftCardsResponse>>

    @DELETE("/api/v1/gift/use")
    suspend fun useGiftCard(
        @Query("giftCode") giftCode: String,
    ): ApiResponse<Unit>
}