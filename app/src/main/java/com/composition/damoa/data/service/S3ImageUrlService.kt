package com.composition.damoa.data.service

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.response.BaseResponse
import com.composition.damoa.data.dto.response.S3ImageUrlsResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface S3ImageUrlService {
    @GET("/api/v1/pet/images/presigned-url")
    suspend fun getPreSignedUrls(
        @Query("imageAmount") imageAmount: Int,
    ): ApiResponse<BaseResponse<S3ImageUrlsResponse>>

    @DELETE("/api/v1/pet/images/delete")
    suspend fun deleteS3ImageDirectory(
        @Query("s3DirectoryPath") s3DirectoryPath: String,
    ): ApiResponse<Unit>
}