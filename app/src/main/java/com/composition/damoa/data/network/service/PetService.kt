package com.composition.damoa.data.network.service

import com.composition.damoa.data.network.dto.request.PetRequest
import com.composition.damoa.data.network.dto.response.BaseResponse
import com.composition.damoa.data.network.dto.response.PetsResponse
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PetService {
    @GET("/api/v1/pets")
    suspend fun getPets(): ApiResponse<BaseResponse<PetsResponse>>

    @POST("/api/v1/pet/add")
    suspend fun addPet(
        @Body request: PetRequest,
    ): ApiResponse<Unit>
}