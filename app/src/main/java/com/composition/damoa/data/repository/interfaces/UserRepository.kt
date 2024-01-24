package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.Account

interface UserRepository {
    suspend fun login(
        socialType: Account.SocialType,
        accessToken: String,
        fcmToken: String,
    ): ApiResponse<Account.Token>
}