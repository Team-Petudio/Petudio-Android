package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.request.GoogleLoginRequest
import com.composition.damoa.data.dto.response.GoogleLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleService {
    @POST("/oauth2/v4/token")
    suspend fun getAccessToken(
        @Body request: GoogleLoginRequest,
    ): ApiResponse<GoogleLoginResponse>

    companion object {
        const val BASE_URL = "https://www.googleapis.com"
    }
}