package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.S3ImageUrls

interface S3ImageUrlRepository {
    suspend fun getPreSignedUrls(imageSize: Int): ApiResponse<S3ImageUrls>

    suspend fun deleteS3ImageDirectory(directoryPath: String): ApiResponse<Unit>
}