package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse

interface GoogleRepository {
    suspend fun getAccessToken(
        authCode: String,
        clientId: String,
        clientSecret: String,
    ): ApiResponse<String>
}