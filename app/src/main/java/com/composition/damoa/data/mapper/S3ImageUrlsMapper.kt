package com.composition.damoa.data.mapper

import com.composition.damoa.data.dto.response.S3ImageUrlsResponse
import com.composition.damoa.data.dto.response.S3ImageUrlsResponse.PreSignedImageUrlResponse
import com.composition.damoa.data.model.S3ImageUrls
import com.composition.damoa.data.model.S3ImageUrls.PreSignedImageUrl

fun S3ImageUrlsResponse.toDomain(): S3ImageUrls = S3ImageUrls(
    s3DirectoryPath = s3DirectoryPath,
    preSignedImageUrls = preSignedImageUrls.map(PreSignedImageUrlResponse::toDomain),
)

private fun PreSignedImageUrlResponse.toDomain(): PreSignedImageUrl = PreSignedImageUrl(
    preSignedUrl = preSignedUrl,
    storedImageUrl = storedImageUri,
)