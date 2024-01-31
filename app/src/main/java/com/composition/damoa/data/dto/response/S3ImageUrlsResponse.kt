package com.composition.damoa.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class S3ImageUrlsResponse(
    @SerialName("s3DirectoryPath")
    val s3DirectoryPath: String,
    @SerialName("petImageUploadInfos")
    val preSignedImageUrls: List<PreSignedImageUrlResponse>,
) {
    @Serializable
    data class PreSignedImageUrlResponse(
        @SerialName("preSignedUrl")
        val preSignedUrl: String,
        @SerialName("imageUri")
        val storedImageUri: String,
    )
}