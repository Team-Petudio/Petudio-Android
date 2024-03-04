package com.composition.damoa.data.network.service

import com.composition.damoa.data.network.dto.request.GoogleLoginRequest
import com.composition.damoa.data.network.dto.response.GoogleLoginResponse
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
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