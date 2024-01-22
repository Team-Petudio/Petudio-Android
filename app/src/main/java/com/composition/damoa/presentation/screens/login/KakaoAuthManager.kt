package com.composition.damoa.presentation.screens.login

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoAuthManager(private val context: Context) {
    private var successCallback: (accessToken: String) -> Unit = {}
    private val loginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> Log.d("buna", "로그인 실패 $error")
            token != null -> successCallback(token.accessToken)
        }
    }

    fun login(onSuccess: (accessToken: String) -> Unit) {
        successCallback = onSuccess
        val userApiClientInstance = UserApiClient.instance

        if (!isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoAccount(context) // 카카오 이메일 로그인
            return
        }

        userApiClientInstance.loginWithKakaoTalk(context) { token, error ->
            when {
                error != null -> handleError(context, error)
                token != null -> onSuccess(token.accessToken)
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
