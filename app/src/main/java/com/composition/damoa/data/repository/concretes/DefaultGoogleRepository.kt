package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.request.GoogleLoginRequest
import com.composition.damoa.data.repository.interfaces.GoogleRepository
import com.composition.damoa.data.service.GoogleService
import javax.inject.Inject

class DefaultGoogleRepository @Inject constructor(
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