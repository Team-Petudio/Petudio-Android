package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.model.User
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse

interface TokenRepository {
    fun getToken(): User.Token

    fun saveToken(token: User.Token)

    fun deleteToken()

    suspend fun reissueToken(): ApiResponse<Unit>
}