package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.User

interface TokenRepository {
    suspend fun reissueToken(): ApiResponse<Unit>
    fun saveToken(token: User.Token)
    fun getToken(): User.Token
    fun deleteToken()
}