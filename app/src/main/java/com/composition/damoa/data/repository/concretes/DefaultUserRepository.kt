package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.dataSource.local.interfaces.TokenDataSource
import com.composition.damoa.data.dto.request.LoginRequest
import com.composition.damoa.data.dto.request.ReissueTokenRequest
import com.composition.damoa.data.model.User
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.data.service.UserService
import okhttp3.Headers
import javax.inject.Inject


class DefaultUserRepository @Inject constructor(
    private val userService: UserService,
    private val tokenDataSource: TokenDataSource,
) : UserRepository {

    override suspend fun login(
        socialType: User.SocialType,
        accessToken: String,
        fcmToken: String,
    ): ApiResponse<User.Token> =
        when (val loginResult = userService.login(LoginRequest(socialType, accessToken, fcmToken))) {
            is Success -> {
                val token = parseToken(loginResult.headers)
                tokenDataSource.saveToken(token)
                Success(token)
            }

            is Failure -> Failure(loginResult.code, loginResult.message)
            NetworkError -> NetworkError
            is Unexpected -> Unexpected(loginResult.error)
        }

    override suspend fun reissueToken(): ApiResponse<Unit> {
        return when (val reissueResult = userService.reissueToken(ReissueTokenRequest(tokenDataSource.getToken()))) {
            is Success -> {
                parseToken(reissueResult.headers).also { tokenDataSource.saveToken(it) }
                Success(Unit)
            }

            else -> reissueResult
        }
    }

    private fun parseToken(headers: Headers): User.Token {
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

    companion object {
        private const val ACCESS_TOKEN = "accessToken"
        private const val REFRESH_TOKEN = "refreshToken"
    }
}