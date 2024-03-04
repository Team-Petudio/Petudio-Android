package com.composition.damoa.data.network.service

import com.composition.damoa.data.network.dto.request.ReissueTokenRequest
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {
    @POST("/api/v1/auth/reissue")
    suspend fun reissueToken(
        @Body request: ReissueTokenRequest,
    ): ApiResponse<Unit>
}