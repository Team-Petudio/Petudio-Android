package com.composition.damoa.data.common.retrofit.interceptor

import android.content.Context
import android.content.Intent
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.repository.interfaces.TokenRepository
import com.composition.damoa.presentation.screens.login.LoginActivity
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    private val context: Context,
    private val tokenRepository: TokenRepository,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenRepository.getToken()
        val response = chain.proceedWithToken(token.accessToken)

        var newResponse = response
        if (response.isInvalidToken()) {
            reissueToken(
                onSuccess = { newAccessToken -> newResponse = chain.proceedWithToken(newAccessToken) },
                onFailure = { navigateToLoginScreen() },
            )
        }
        return newResponse
    }

    private fun reissueToken(
        onSuccess: (accessToken: String) -> Unit,
        onFailure: () -> Unit,
    ) {
        runBlocking {
            when (tokenRepository.reissueToken()) {
                is Success -> onSuccess(tokenRepository.getToken().accessToken)
                else -> onFailure()
            }
        }
    }

    private fun Interceptor.Chain.proceedWithToken(token: String): Response {
        return proceed(request().putAccessToken(token))
    }

    private fun Request.putAccessToken(
        token: String,
    ): Request = putHeader(ACCESS_TOKEN_HEADER, token)

    private fun Request.putHeader(
        key: String,
        value: String,
    ): Request = newBuilder().addHeader(key, value).build()

    private fun Response.isInvalidToken(): Boolean = (code == 401)

    private fun navigateToLoginScreen() {
        val loginStartIntent = Intent(context, LoginActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(loginStartIntent)
    }

    companion object {
        private const val ACCESS_TOKEN_HEADER = "authorization"
    }
}
