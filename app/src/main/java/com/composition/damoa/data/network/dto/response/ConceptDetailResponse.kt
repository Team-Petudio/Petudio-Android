package com.composition.damoa.data.network.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConceptDetailResponse(
    @SerialName("successImageUris")
    val successImageUrls: List<String>,
    @SerialName("failImageUris")
    val failImageUrls: List<String>,
)