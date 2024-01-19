package com.composition.damoa.presentation.screens.login

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoAuthManager {
    private val loginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> Log.d("buna", "로그인 실패 $error")
            token != null -> Log.d("buna", "로그인 성공 ${token.accessToken}")
        }
    }

    fun login(
        context: Context,
        onSuccess: (accessToken: String) -> Unit,
    ) {
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
        Log.d("buna", "로그인 실패 $error")
        // 사용자 취소로 인한 종료
        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) return
        loginWithKakaoAccount(context) // 카카오 이메일 로그인
    }

    private fun loginWithKakaoAccount(context: Context) {
        val userApiClientInstance = UserApiClient.instance
        userApiClientInstance.loginWithKakaoAccount(context, callback = loginCallback)
    }
}
