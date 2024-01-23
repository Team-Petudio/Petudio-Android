package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse

interface GoogleRepository {
    suspend fun getAccessToken(
        authCode: String,
        clientId: String,
        clientSecret: String,
    ): ApiResponse<String>
}