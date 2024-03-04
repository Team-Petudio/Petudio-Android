package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.mapper.toDomain
import com.composition.damoa.data.model.S3ImageUrls
import com.composition.damoa.data.network.dto.request.PreSignedUrlsRequest
import com.composition.damoa.data.network.dto.request.S3DirectoryDeleteRequest
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.network.service.S3ImageUrlService
import com.composition.damoa.data.repository.interfaces.S3ImageUrlRepository

class DefaultS3ImageUrlRepository(
    private val service: S3ImageUrlService,
) : S3ImageUrlRepository {

    override suspend fun getPreSignedUrls(
        imageSize: Int,
    ): ApiResponse<S3ImageUrls> = service
        .getPreSignedUrls(PreSignedUrlsRequest(imageSize))
        .map { response -> response.data.toDomain() }

    override suspend fun deleteS3ImageDirectory(
        directoryPath: String,
    ): ApiResponse<Unit> = service.deleteS3ImageDirectory(
        request = S3DirectoryDeleteRequest(directoryPath)
    )
}