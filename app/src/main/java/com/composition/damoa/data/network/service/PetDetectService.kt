package com.composition.damoa.data.network.service

import com.composition.damoa.data.network.dto.response.PetDetectResponse
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface PetDetectService {
    @Multipart
    @POST("/detect")
    suspend fun detectPet(
        @Query("petType") petType: String,
        @Part petImages: List<MultipartBody.Part>,
    ): ApiResponse<PetDetectResponse>

    companion object {
        const val BASE_URL = "http://3.34.124.220/"
    }
}