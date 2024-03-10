package com.composition.damoa.presentation.screens.login.authManager

import android.content.Context
import com.composition.damoa.data.model.User
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient


class KakaoAuthManager(
    private val context: Context,
) : AuthManager() {
    private val loginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> fail(error)
            token != null -> FirebaseMessaging.getInstance().token.addOnSuccessListener { fcmToken ->
                success(token.accessToken, fcmToken)
            }
        }
    }

    override fun login(
        socialType: User.SocialType,
        onPreLogin: () -> Unit,
        onSuccess: (accessToken: String, fcmToken: String) -> Unit,
        onFailure: (error: Throwable) -> Unit,
    ) {
        super.login(socialType, onPreLogin, onSuccess, onFailure)

        val userApiClientInstance = UserApiClient.instance

        if (!isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoAccount(context) // 카카오 이메일 로그인
            return
        }

        userApiClientInstance.loginWithKakaoTalk(context) { token, error ->
            when {
                error != null -> handleError(context, error)
                token != null -> FirebaseMessaging.getInstance().token.addOnSuccessListener { fcmToken ->
                    success(token.accessToken, fcmToken)
                }
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
