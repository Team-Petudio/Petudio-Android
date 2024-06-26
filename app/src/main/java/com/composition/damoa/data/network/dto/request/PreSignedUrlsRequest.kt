package com.composition.damoa.data.network.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreSignedUrlsRequest(
    @SerialName("imageAmount")
    val imageAmount: Int,
)
