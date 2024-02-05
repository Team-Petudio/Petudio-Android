package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.response.PetDetectResponse
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
        @Part imageMultiParts: List<MultipartBody.Part>,
    ): ApiResponse<PetDetectResponse>

    companion object {
        const val BASE_URL = "http://3.34.124.220/"
    }
}