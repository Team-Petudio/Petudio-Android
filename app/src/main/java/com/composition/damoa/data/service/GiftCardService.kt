package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.response.BaseResponse
import com.composition.damoa.data.dto.response.GiftCardsResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface GiftCardService {
    @GET("/api/v1/gifts")
    suspend fun getGiftCards(): ApiResponse<BaseResponse<GiftCardsResponse>>

    @DELETE("/api/v1/gift/user")
    suspend fun useGiftCard(@Query("giftCode") giftCode: String): ApiResponse<Unit>
}