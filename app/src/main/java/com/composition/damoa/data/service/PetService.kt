package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.request.PetRequest
import com.composition.damoa.data.dto.response.BaseResponse
import com.composition.damoa.data.dto.response.PetsResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface PetService {
    @GET("/api/v1/pets")
    suspend fun getPets(): ApiResponse<BaseResponse<PetsResponse>>

    @POST("/api/v1/pet/add")
    suspend fun addPet(request: PetRequest): ApiResponse<Unit>
}