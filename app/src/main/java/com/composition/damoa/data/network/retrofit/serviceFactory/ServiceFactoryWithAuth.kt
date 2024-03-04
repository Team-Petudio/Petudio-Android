package com.composition.damoa.data.network.retrofit.serviceFactory

import com.composition.damoa.data.network.retrofit.interceptor.AuthInterceptor
import com.composition.damoa.data.network.retrofit.interceptor.LocaleInterceptor
import com.composition.damoa.data.repository.interfaces.TokenRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class ServiceFactoryWithAuth(
    tokenRepository: TokenRepository,
) : ServiceFactory() {
    private val okhttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(AuthInterceptor(tokenRepository))
        .addInterceptor(LocaleInterceptor())
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build()

    override fun getRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(petudioCallAdapterFactory)
        .client(okhttpClient)
        .build()

    override fun <T> create(service: Class<T>, baseUrl: String): T {
        if (baseUrl == BASE_URL) return retrofit.create(service)
        return getRetrofit(baseUrl).create(service)
    }
}
