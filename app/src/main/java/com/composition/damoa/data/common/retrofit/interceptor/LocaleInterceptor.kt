package com.composition.damoa.data.common.retrofit.interceptor

import androidx.compose.ui.text.intl.Locale
import okhttp3.Interceptor
import okhttp3.Response

class LocaleInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header(HEADER_ACCEPT_LANGUAGE, Locale.current.language)
            .build()
        return chain.proceed(request)
    }

    companion object {
        const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
    }
}