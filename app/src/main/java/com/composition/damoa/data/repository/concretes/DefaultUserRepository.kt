package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.dto.request.LoginRequest
import com.composition.damoa.data.model.User
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.data.service.UserService
import okhttp3.Headers
import javax.inject.Inject


class DefaultUserRepository @Inject constructor(
    private val userService: UserService,
) : UserRepository {
    override suspend fun login(
        socialType: User.SocialType,
        accessToken: String,
        fcmToken: String,
    ): ApiResponse<User.Token> =
        when (val loginResult = userService.login(LoginRequest(socialType, accessToken, fcmToken))) {
            is Success -> Success(parseToken(loginResult.headers))
            is Failure -> Failure(loginResult.code, loginResult.message)
            NetworkError -> NetworkError
            is Unexpected -> Unexpected(loginResult.error)
        }

    private fun parseToken(headers: Headers): User.Token {
        val tokens = headers
            .filter { (key, _) -> key == "Set-Cookie" }
            .mapNotNull { (_, cookieValue) ->
                val tokenInfo = cookieValue.split(";")[0].split("=")
                val tokenType = tokenInfo[0]
                val token = tokenInfo[1]

                when (tokenType) {
                    "accessToken", "refreshToken" -> Pair(tokenType, token)
                    else -> null
                }
            }.toMap()

        return User.Token(
            accessToken = tokens["accessToken"] ?: "",
            refreshToken = tokens["refreshToken"] ?: ""
        )
    }
}