package com.composition.damoa.data.network.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PetDetectResponse(
    @SerialName("results")
    val detectResults: List<Boolean>,
)