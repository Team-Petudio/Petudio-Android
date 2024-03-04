package com.composition.damoa.data.network.retrofit.serviceFactory

import com.composition.damoa.data.network.retrofit.callAdapter.PetudioCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

abstract class ServiceFactory {
    private val json = Json {
        coerceInputValues = true
        encodeDefaults = true
        isLenient = true
    }
    private val jsonMediaType = "application/json".toMediaType()
    protected val jsonConverterFactory = json.asConverterFactory(jsonMediaType)
    protected val petudioCallAdapterFactory = PetudioCallAdapterFactory()
    protected val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    protected val retrofit: Retrofit by lazy { getRetrofit(BASE_URL) }

    protected abstract fun getRetrofit(baseUrl: String): Retrofit

    abstract fun <T> create(service: Class<T>, baseUrl: String = BASE_URL): T

    companion object {
        const val BASE_URL = "http://52.79.242.131/"
    }
}