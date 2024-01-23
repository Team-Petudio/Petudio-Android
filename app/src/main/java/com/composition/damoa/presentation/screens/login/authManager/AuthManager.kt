package com.composition.damoa.presentation.screens.login.authManager

abstract class AuthManager {
    protected var successCallback: (accessToken: String) -> Unit = {}
    protected var failureCallback: (error: Throwable) -> Unit = {}

    open fun login(
        onSuccess: (accessToken: String) -> Unit,
        onFailure: (error: Throwable) -> Unit,
    ) {
        successCallback = onSuccess
        failureCallback = onFailure
    }
}