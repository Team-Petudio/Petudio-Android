package com.composition.damoa.presentation.screens.login.authManager

import com.composition.damoa.data.model.User.SocialType


class AuthCompat(
    vararg authManagers: Pair<SocialType, AuthManager>,
) : AuthManager() {
    private val authManagers: Map<SocialType, AuthManager> = authManagers.toMap()

    override fun login(
        socialType: SocialType,
        onPreLogin: () -> Unit,
        onSuccess: (accessToken: String, fcmToken: String) -> Unit,
        onFailure: (error: Throwable) -> Unit,
    ) {
        authManagers.getValue(socialType).login(
            socialType = socialType,
            onPreLogin = onPreLogin,
            onSuccess = onSuccess,
            onFailure = onFailure,
        )
    }
}