package com.composition.damoa.data.model

data class S3ImageUrls(
    val s3DirectoryPath: String,
    val preSignedImageUrls: List<PreSignedImageUrl>,
) {
    data class PreSignedImageUrl(
        val preSignedUrl: String,
        val storedImageUrl: String,
    )

    init {
        validateS3ImageUrls()
    }

    private fun validateS3ImageUrls() {
        check(preSignedImageUrls.size in MIN_S3_IMAGE_URL_SIZE..MAX_S3_IMAGE_URL_SIZE) {
            "[ERROR] S3ImageUrl 사이즈가 10개 이상 12개 이하가 아닙니다."
        }
    }

    companion object {
        private const val MIN_S3_IMAGE_URL_SIZE = 10
        private const val MAX_S3_IMAGE_URL_SIZE = 12
    }
}