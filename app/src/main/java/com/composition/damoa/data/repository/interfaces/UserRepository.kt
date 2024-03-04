package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.model.User
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse

interface UserRepository {
    suspend fun login(
        socialType: User.SocialType,
        accessToken: String,
        fcmToken: String,
    ): ApiResponse<User.Token>

    suspend fun logout(): ApiResponse<Unit>

    suspend fun getUser(): ApiResponse<User>

    suspend fun signOut(): ApiResponse<Unit>

    suspend fun updateNotificationStatus(newStatus: Boolean): ApiResponse<Boolean>
}