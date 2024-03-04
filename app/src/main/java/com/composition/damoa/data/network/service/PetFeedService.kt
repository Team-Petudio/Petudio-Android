package com.composition.damoa.data.network.service

import com.composition.damoa.data.network.dto.response.BaseResponse
import com.composition.damoa.data.network.dto.response.PetFeedsResponse
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
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