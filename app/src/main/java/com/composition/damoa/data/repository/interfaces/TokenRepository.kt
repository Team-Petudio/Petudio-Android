package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.model.User
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse

interface TokenRepository {
    suspend fun getToken(): User.Token

    suspend fun saveToken(token: User.Token)

    suspend fun deleteToken()

    suspend fun reissueToken(): ApiResponse<Unit>
}