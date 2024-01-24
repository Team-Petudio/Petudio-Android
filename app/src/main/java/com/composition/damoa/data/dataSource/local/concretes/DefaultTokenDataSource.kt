package com.composition.damoa.data.dataSource.local.concretes

import android.content.SharedPreferences
import com.composition.damoa.data.dataSource.local.interfaces.TokenDataSource
import com.composition.damoa.data.model.User
import javax.inject.Inject

class DefaultTokenDataSource @Inject constructor(
    private val preference: SharedPreferences,
) : TokenDataSource {
    private val preferenceEditor = preference.edit()

    override fun saveToken(token: User.Token) {
        preferenceEditor.putString(ACCESS_TOKEN_KEY, token.accessToken).apply()
        preferenceEditor.putString(REFRESH_TOKEN_KEY, token.refreshToken).apply()
    }

    override fun getToken(): User.Token {
        val accessToken = TOKEN_FORMAT.format(preference.getString(ACCESS_TOKEN_KEY, DEFAULT_TOKEN_VALUE))
        val refreshToken = TOKEN_FORMAT.format(preference.getString(REFRESH_TOKEN_KEY, DEFAULT_TOKEN_VALUE))

        return User.Token(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    override fun deleteToken() {
        preferenceEditor.remove(ACCESS_TOKEN_KEY).apply()
        preferenceEditor.remove(REFRESH_TOKEN_KEY).apply()
    }

    companion object {
        private const val TOKEN_FORMAT = "Bearer %s"

        private const val ACCESS_TOKEN_KEY = "access_token_key"
        private const val REFRESH_TOKEN_KEY = "refresh_token_key"
        private const val DEFAULT_TOKEN_VALUE = "default"
    }
}