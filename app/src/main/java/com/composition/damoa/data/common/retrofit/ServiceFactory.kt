package com.composition.damoa.data.common.retrofit

import com.composition.damoa.data.common.retrofit.callAdapter.PetudioCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class ServiceFactory {
    private val json = Json {
        coerceInputValues = true
        encodeDefaults = true
        isLenient = true
    }
    private val jsonMediaType = "application/json".toMediaType()
    private val jsonConverterFactory = json.asConverterFactory(jsonMediaType)

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okhttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = getRetrofit(BASE_URL)

    private fun getRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(PetudioCallAdapterFactory())
        .client(okhttpClient)
        .build()

    fun <T> create(service: Class<T>, baseUrl: String = BASE_URL): T {
        if (baseUrl == BASE_URL) return retrofit.create(service)
        return getRetrofit(baseUrl).create(service)
    }

    companion object {
        private const val BASE_URL = "http://13.125.155.71/"
    }
}
