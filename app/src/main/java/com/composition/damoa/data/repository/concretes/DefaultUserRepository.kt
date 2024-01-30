package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.common.retrofit.callAdapter.Failure
import com.composition.damoa.data.common.retrofit.callAdapter.NetworkError
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.common.retrofit.callAdapter.TokenExpired
import com.composition.damoa.data.common.retrofit.callAdapter.Unexpected
import com.composition.damoa.data.common.utils.TokenParser
import com.composition.damoa.data.dto.request.LoginRequest
import com.composition.damoa.data.mapper.toDomain
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
            TokenExpired -> TokenExpired
            is Unexpected -> Unexpected(loginResult.error)
        }

    override suspend fun logout(): ApiResponse<Unit> {
        return userService.logout().map { tokenRepository.deleteToken() }
    }

    override suspend fun getUser(): ApiResponse<User> {
        val token = tokenRepository.getToken()
        return userService.getUser().map { it.data.toDomain(token) }
    }

    override suspend fun signOut(): ApiResponse<Unit> {
        return userService.signOut().map { tokenRepository.deleteToken() }
    }

    override suspend fun updateNotificationStatus(
        newStatus: Boolean,
    ): ApiResponse<Boolean> = userService
        .updateNotificationStatus(newStatus)
        .map { it.data.notificationStatus }
}