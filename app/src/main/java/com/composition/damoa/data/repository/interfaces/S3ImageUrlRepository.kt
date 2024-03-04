package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.model.S3ImageUrls
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse

interface S3ImageUrlRepository {
    suspend fun getPreSignedUrls(imageSize: Int): ApiResponse<S3ImageUrls>

    suspend fun deleteS3ImageDirectory(directoryPath: String): ApiResponse<Unit>
}