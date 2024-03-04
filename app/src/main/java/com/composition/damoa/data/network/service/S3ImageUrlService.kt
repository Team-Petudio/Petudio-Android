package com.composition.damoa.data.network.service

import com.composition.damoa.data.network.dto.request.PreSignedUrlsRequest
import com.composition.damoa.data.network.dto.request.S3DirectoryDeleteRequest
import com.composition.damoa.data.network.dto.response.BaseResponse
import com.composition.damoa.data.network.dto.response.S3ImageUrlsResponse
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST

interface S3ImageUrlService {
    @POST("/api/v1/pet/images/presigned-url")
    suspend fun getPreSignedUrls(
        @Body request: PreSignedUrlsRequest,
    ): ApiResponse<BaseResponse<S3ImageUrlsResponse>>

    @HTTP(method = "DELETE", path = "/api/v1/pet/images/delete", hasBody = true)
    suspend fun deleteS3ImageDirectory(
        @Body request: S3DirectoryDeleteRequest,
    ): ApiResponse<Unit>
}