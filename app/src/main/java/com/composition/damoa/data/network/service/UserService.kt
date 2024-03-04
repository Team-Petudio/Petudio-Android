package com.composition.damoa.data.network.service

import com.composition.damoa.data.network.dto.request.LoginRequest
import com.composition.damoa.data.network.dto.response.BaseResponse
import com.composition.damoa.data.network.dto.response.NotificationStatusUpdateResponse
import com.composition.damoa.data.network.dto.response.UserResponse
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): ApiResponse<Unit>

    @POST("/api/v1/auth/logout")
    suspend fun logout(): ApiResponse<Unit>

    @GET("/api/v1/member/mypage")
    suspend fun getUser(): ApiResponse<BaseResponse<UserResponse>>

    @DELETE("/api/v1/member/delete")
    suspend fun signOut(): ApiResponse<Unit>

    @PATCH("/api/v1/member/notification/status-change")
    suspend fun updateNotificationStatus(
        @Query("status") newStatus: Boolean,
    ): ApiResponse<BaseResponse<NotificationStatusUpdateResponse>>
}