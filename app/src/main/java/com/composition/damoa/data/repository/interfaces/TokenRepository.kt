package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.User

interface TokenRepository {
    fun getToken(): User.Token

    fun saveToken(token: User.Token)

    fun deleteToken()

    suspend fun reissueToken(): ApiResponse<Unit>
}