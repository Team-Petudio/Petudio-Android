package com.composition.damoa.data.network.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class S3DirectoryDeleteRequest(
    @SerialName("s3DirectoryPath")
    val s3DirectoryPath: String,
)
