package com.composition.damoa.data.common.utils

import com.composition.damoa.data.model.User
import okhttp3.Headers

object TokenParser {
    private const val ACCESS_TOKEN = "accessToken"
    private const val REFRESH_TOKEN = "refreshToken"

    fun parseHeaders(headers: Headers): User.Token {
        val tokens = headers
            .filter { (key, _) -> key == "Set-Cookie" }
            .mapNotNull { (_, cookieValue) ->
                val tokenInfo = cookieValue.split(";")[0].split("=")
                val tokenType = tokenInfo[0]
                val token = tokenInfo[1]

                when (tokenType) {
                    ACCESS_TOKEN, REFRESH_TOKEN -> Pair(tokenType, token)
                    else -> null
                }
            }.toMap()

        return User.Token(
            accessToken = tokens[ACCESS_TOKEN] ?: "",
            refreshToken = tokens[REFRESH_TOKEN] ?: ""
        )
    }
}