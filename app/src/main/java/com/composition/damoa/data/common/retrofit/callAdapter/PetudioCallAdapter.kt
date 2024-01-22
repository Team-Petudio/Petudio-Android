package com.composition.damoa.data.common.retrofit.callAdapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class PetudioCallAdapter<T : Any>(
    private val responseType: Type,
) : CallAdapter<T, Call<ApiResponse<T>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Call<ApiResponse<T>> = PetudioCall(call, responseType)
}
