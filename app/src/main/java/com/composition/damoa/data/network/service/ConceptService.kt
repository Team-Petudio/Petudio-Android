package com.composition.damoa.data.network.service

import com.composition.damoa.data.network.dto.response.BaseResponse
import com.composition.damoa.data.network.dto.response.ConceptDetailResponse
import com.composition.damoa.data.network.dto.response.ConceptsResponse
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ConceptService {
    @GET("/api/v1/concepts")
    suspend fun getConcepts(): ApiResponse<BaseResponse<ConceptsResponse>>

    @GET("/api/v1/concept/detail/{conceptId}")
    suspend fun getConceptDetail(
        @Path("conceptId") conceptId: Long,
    ): ApiResponse<BaseResponse<ConceptDetailResponse>>
}