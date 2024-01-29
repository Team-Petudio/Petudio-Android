package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.response.BaseResponse
import com.composition.damoa.data.dto.response.ConceptsResponse
import retrofit2.http.GET

interface ConceptService {
    @GET("/api/v1/concepts")
    suspend fun getConcepts(): ApiResponse<BaseResponse<ConceptsResponse>>
}