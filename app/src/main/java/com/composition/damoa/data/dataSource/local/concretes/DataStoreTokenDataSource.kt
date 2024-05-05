package com.composition.damoa.data.dataSource.local.concretes

import androidx.datastore.core.DataStore
import com.composition.damoa.data.dataSource.local.interfaces.TokenDataSource
import com.composition.damoa.data.model.User.Token
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DataStoreTokenDataSource(
    private val scope: CoroutineScope,
    private val dataStore: DataStore<Token>,
) : TokenDataSource {

    override fun saveToken(token: Token) {
        scope.launch {
            dataStore.updateData {
                Token(
                    accessToken = token.accessToken,
                    refreshToken = token.refreshToken,
                )
            }
        }
    }

    override suspend fun getToken(): Token {
        return dataStore.data.first()
    }

    override fun deleteToken() {
        scope.launch {
            dataStore.updateData {
                Token()
            }
        }
    }
}