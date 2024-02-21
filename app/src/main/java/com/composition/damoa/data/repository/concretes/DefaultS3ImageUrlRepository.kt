package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.request.PreSignedUrlsRequest
import com.composition.damoa.data.dto.request.S3DirectoryDeleteRequest
import com.composition.damoa.data.mapper.toDomain
import com.composition.damoa.data.model.S3ImageUrls
import com.composition.damoa.data.repository.interfaces.S3ImageUrlRepository
import com.composition.damoa.data.service.S3ImageUrlService

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