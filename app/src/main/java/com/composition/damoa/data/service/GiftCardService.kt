package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.response.BaseResponse
import com.composition.damoa.data.dto.response.GiftCardsResponse
import retrofit2.http.GET

interface GiftCardService {
    @GET("/api/v1/gifts")
    suspend fun getGiftCards(): ApiResponse<BaseResponse<GiftCardsResponse>>
}