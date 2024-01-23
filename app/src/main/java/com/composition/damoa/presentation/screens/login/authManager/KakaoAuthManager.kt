package com.composition.damoa.presentation.screens.login.authManager

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoAuthManager(
    private val context: Context,
) : AuthManager() {
    private val loginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> failureCallback(error)
            token != null -> successCallback(token.accessToken)
        }
    }

    override fun login(
        onSuccess: (accessToken: String) -> Unit,
        onFailure: (error: Throwable) -> Unit,
    ) {
        super.login(onSuccess, onFailure)

        val userApiClientInstance = UserApiClient.instance

        if (!isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoAccount(context) // 카카오 이메일 로그인
            return
        }

        userApiClientInstance.loginWithKakaoTalk(context) { token, error ->
            when {
                error != null -> handleError(context, error)
                token != null -> successCallback(token.accessToken)
            }
        }
    }

    private fun isKakaoTalkLoginAvailable(context: Context): Boolean {
        return UserApiClient.instance.isKakaoTalkLoginAvailable(context)
    }

    private fun handleError(
        context: Context,
        error: Throwable,
    ) {
        if (isUserCancelled(error)) return
        loginWithKakaoAccount(context) // 카카오 이메일 로그인
    }

    private fun isUserCancelled(error: Throwable): Boolean {
        return error is ClientError && error.reason == ClientErrorCause.Cancelled
    }

    private fun loginWithKakaoAccount(context: Context) {
        val userApiClientInstance = UserApiClient.instance
        userApiClientInstance.loginWithKakaoAccount(context, callback = loginCallback)
    }
}
