package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.request.PreSignedUrlsRequest
import com.composition.damoa.data.dto.request.S3DirectoryDeleteRequest
import com.composition.damoa.data.dto.response.BaseResponse
import com.composition.damoa.data.dto.response.S3ImageUrlsResponse
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