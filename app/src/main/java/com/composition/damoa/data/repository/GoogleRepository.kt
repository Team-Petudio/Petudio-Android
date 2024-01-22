package com.composition.damoa.data.repository

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.request.GoogleLoginRequest
import com.composition.damoa.data.service.GoogleService

class GoogleRepository(
    private val service: GoogleService,
) {
    suspend fun getAccessToken(
        authCode: String,
        clientId: String,
        clientSecret: String,
    ): ApiResponse<String> = service.getAccessToken(
        request = GoogleLoginRequest(
            grantType = "authorization_code",
            clientId = clientId,
            clientSecret = clientSecret,
            code = authCode,
        )
    ).map { it.accessToken }
}