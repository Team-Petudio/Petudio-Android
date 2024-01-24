package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.request.LoginRequest
import com.composition.damoa.data.dto.request.ReissueTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<Unit>

    @POST("/api/v1/auth/logout")
    suspend fun logout(): ApiResponse<Unit>

    @POST("/api/v1/auth/reissue")
    suspend fun reissueToken(@Body request: ReissueTokenRequest): ApiResponse<Unit>
}