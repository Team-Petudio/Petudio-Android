package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.request.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<Unit>
}