package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.dto.request.LoginRequest
import com.composition.damoa.data.model.Account
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.data.service.UserService
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val userService: UserService,
) : UserRepository {
    override suspend fun login(
        socialType: Account.SocialType,
        accessToken: String,
        fcmToken: String,
    ) {
        userService.login(LoginRequest(socialType, accessToken, fcmToken))
    }
}