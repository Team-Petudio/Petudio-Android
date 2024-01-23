package com.composition.damoa.data.service

import com.composition.damoa.data.dto.request.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/v1/users/login")
    suspend fun login(@Body request: LoginRequest)
}