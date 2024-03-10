package com.composition.damoa.presentation.screens.login.authManager

import com.composition.damoa.data.model.User
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

abstract class AuthManager {
    private val mainScope = MainScope()

    private var onPreLoginCallback: () -> Unit = {}
    private var successCallback: (accessToken: String, fcmToken: String) -> Unit = { _, _ -> }
    private var failureCallback: (error: Throwable) -> Unit = {}

    open fun login(
        socialType: User.SocialType,
        onPreLogin: () -> Unit,
        onSuccess: (accessToken: String, fcmToken: String) -> Unit,
        onFailure: (error: Throwable) -> Unit,
    ) {
        onPreLoginCallback = onPreLogin
        successCallback = onSuccess
        failureCallback = onFailure

        preLogin()
    }

    private fun preLogin() {
        onPreLoginCallback()
    }

    protected fun success(accessToken: String, fcmToken: String) {
        successCallback(accessToken, fcmToken)
    }

    protected fun fail(error: Throwable) {
        mainScope.launch { failureCallback(error) }
    }
}