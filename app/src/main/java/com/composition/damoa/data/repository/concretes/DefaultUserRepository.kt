package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.common.utils.TokenParser
import com.composition.damoa.data.dto.request.LoginRequest
import com.composition.damoa.data.model.User
import com.composition.damoa.data.repository.interfaces.TokenRepository
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.data.service.UserService


class DefaultUserRepository(
    private val userService: UserService,
    private val tokenRepository: TokenRepository,
) : UserRepository {

    override suspend fun login(
        socialType: User.SocialType,
        accessToken: String,
        fcmToken: String,
    ): ApiResponse<User.Token> =
        when (val loginResult = userService.login(LoginRequest(socialType, accessToken, fcmToken))) {
            is Success -> {
                val token = TokenParser.parseToken(loginResult.headers)
                tokenRepository.saveToken(token)
                Success(token)
            }

            is Failure -> Failure(loginResult.code, loginResult.message)
            NetworkError -> NetworkError
            is Unexpected -> Unexpected(loginResult.error)
        }

    override suspend fun logout(): ApiResponse<Unit> {
        return when (val logoutResult = userService.logout()) {
            is Success -> {
                tokenRepository.deleteToken()
                Success(Unit)
            }

            else -> logoutResult
        }
    }
}