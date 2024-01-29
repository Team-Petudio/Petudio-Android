package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.User

interface UserRepository {
    suspend fun login(
        socialType: User.SocialType,
        accessToken: String,
        fcmToken: String,
    ): ApiResponse<User.Token>

    suspend fun logout(): ApiResponse<Unit>

    suspend fun getUser(): ApiResponse<User>

    suspend fun signOut(): ApiResponse<Unit>

    suspend fun updateNotificationStatus(
        newStatus: Boolean,
    ): ApiResponse<Boolean>
}