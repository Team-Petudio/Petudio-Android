package com.composition.damoa.data.dataSource.local.interfaces

import com.composition.damoa.data.model.User

interface TokenDataSource {
    suspend fun saveToken(token: User.Token)
    suspend fun getToken(): User.Token
    suspend fun deleteToken()
}
