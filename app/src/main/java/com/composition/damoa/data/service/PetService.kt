package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.response.BaseResponse
import com.composition.damoa.data.dto.response.PetsResponse
import retrofit2.http.GET

interface PetService {
    @GET("/api/v1/pets")
    suspend fun getPets(): ApiResponse<BaseResponse<PetsResponse>>
}