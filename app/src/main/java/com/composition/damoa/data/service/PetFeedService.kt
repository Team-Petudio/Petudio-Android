package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.response.BaseResponse
import com.composition.damoa.data.dto.response.PetFeedsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PetFeedService {
    @GET("/api/v1/feeds")
    suspend fun getPetFeeds(
        @Query("memberId") userId: Long,
    ): ApiResponse<BaseResponse<PetFeedsResponse>>
}