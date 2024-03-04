package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.network.dto.request.GoogleLoginRequest
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.network.service.GoogleService
import com.composition.damoa.data.repository.interfaces.GoogleRepository

class DefaultGoogleRepository(
    private val service: GoogleService,
) : GoogleRepository {

    override suspend fun getAccessToken(
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