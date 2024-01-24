package com.composition.damoa.data.common.retrofit.interceptor

import android.content.Context
import android.content.Intent
import android.util.Log
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.model.User
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
//        val token = tokenRepository.getToken()
        val token = User.Token(
            "Bearer ayJtZW1iZXJJZCI6MSwiZXhwIjoxNzA2MDgwNjkyfQ.i6LIUfFmbDDr1wxcTxAA-N7m3oNzYnyaonBGu6na0frrDjXf8J0HQn7VVBUgYJZ3LtkO8S7lso4-WLmEDb84rg",
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MDg2NzA4OTJ9.guSSVNA-m-wPNQzgtvMTXM0v2IHhxYETKDCtt3vrdd2YcAEIMZNe0Ye13JY2GDNj0EazaF3W3LJdPziITIeYew"
        )
        val tokenAddedRequest = chain.request().putAccessToken(token.accessToken)
        val response = chain.proceed(tokenAddedRequest)

        if (response.isInvalidToken()) {
            runBlocking {
                when (tokenRepository.reissueToken()) {
                    is Success -> {
                        Log.d("buna", "성공!")
                        val token2 = tokenRepository.getToken()
                        val tokenAddedRequest2 = chain.request().putAccessToken(token2.accessToken)
                        chain.proceed(tokenAddedRequest2)
                    }

                    else -> {
                        Log.d("buna", "불가!")
                        navigateToLoginScreen()
                    }
                }
            }
        }
        return response
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
