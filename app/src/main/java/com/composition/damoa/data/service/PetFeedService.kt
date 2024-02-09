package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.response.BaseResponse
import com.composition.damoa.data.dto.response.PetFeedsResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface PetFeedService {
    @GET("/api/v1/feeds")
    suspend fun getPetFeeds(
        @Query("memberId") userId: Long,
    ): ApiResponse<BaseResponse<PetFeedsResponse>>

    @PATCH("/api/v1/feed/like/{feedId}")
    suspend fun toggleLike(
        @Path("feedId") petFeedId: Long,
    ): ApiResponse<Unit>
}