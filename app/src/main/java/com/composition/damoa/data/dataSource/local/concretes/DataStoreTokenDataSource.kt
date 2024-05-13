package com.composition.damoa.data.dataSource.local.concretes

import androidx.datastore.core.DataStore
import com.composition.damoa.data.dataSource.local.interfaces.TokenDataSource
import com.composition.damoa.data.model.User.Token
import kotlinx.coroutines.flow.first

class DataStoreTokenDataSource(
    private val dataStore: DataStore<Token>,
) : TokenDataSource {

    override suspend fun saveToken(token: Token) {
        dataStore.updateData {
            Token(
                accessToken = token.accessToken,
                refreshToken = token.refreshToken,
            )
        }
    }

    override suspend fun getToken(): Token {
        return dataStore.data.first()
    }

    override suspend fun deleteToken() {
        dataStore.updateData {
            Token()
        }
    }
}