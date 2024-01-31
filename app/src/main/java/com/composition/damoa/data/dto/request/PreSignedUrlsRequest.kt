package com.composition.damoa.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreSignedUrlsRequest(
    @SerialName("imageAmount")
    val imageAmount: Int,
)
