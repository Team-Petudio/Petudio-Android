package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.request.LoginRequest
import com.composition.damoa.data.dto.response.BaseResponse
import com.composition.damoa.data.dto.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("/api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<Unit>

    @POST("/api/v1/auth/logout")
    suspend fun logout(): ApiResponse<Unit>

    @GET("/api/v1/member/mypage")
    suspend fun getUser(): ApiResponse<BaseResponse<UserResponse>>

    @DELETE("/api/v1/member/delete")
    suspend fun signOut(): ApiResponse<Unit>
}