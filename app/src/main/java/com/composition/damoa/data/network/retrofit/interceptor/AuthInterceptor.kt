package com.composition.damoa.data.network.retrofit.interceptor

import com.composition.damoa.data.network.retrofit.callAdapter.Success
import com.composition.damoa.data.repository.interfaces.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    private val tokenRepository: TokenRepository,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenRepository.getToken()
        }
        val response = chain.proceedWithToken(TOKEN_FORMAT.format(token.accessToken))

        var newResponse = response
        if (response.isInvalidToken()) {
            reissueToken(
                onSuccess = { accessToken ->
                    response.close()
                    newResponse = chain.proceedWithToken(TOKEN_FORMAT.format(accessToken))
                },
            )
        }
        return newResponse
    }

    private fun reissueToken(
        onSuccess: (accessToken: String) -> Unit,
    ) {
        runBlocking {
            when (tokenRepository.reissueToken()) {
                is Success -> onSuccess(tokenRepository.getToken().accessToken)
                else -> Unit
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

    companion object {
        private const val ACCESS_TOKEN_HEADER = "authorization"
        private const val TOKEN_FORMAT = "Bearer %s"
    }
}
