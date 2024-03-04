package com.composition.damoa.data.network.service

import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface S3ImageService {
    @Multipart
    @POST("/{directoryPath}")
    suspend fun uploadImage(
        @Path("directoryPath") directoryPath: String,
        @Part imageFile: MultipartBody.Part,
    ): ApiResponse<Unit>

    companion object {
        const val BASE_URL = "https://petudio-s3.53.ap-northeast-2.amazonaws.com/"
    }
}