package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.request.ReissueTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {
    @POST("/api/v1/auth/reissue")
    suspend fun reissueToken(
        @Body request: ReissueTokenRequest,
    ): ApiResponse<Unit>
}