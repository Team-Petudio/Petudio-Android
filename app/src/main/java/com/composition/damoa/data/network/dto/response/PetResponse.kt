package com.composition.damoa.data.network.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PetsResponse(
    @SerialName("petsInfo")
    val pets: List<PetResponse>,
) {
    @Serializable
    data class PetResponse(
        @SerialName("petId")
        val id: Long,
        @SerialName("name")
        val name: String,
        @SerialName("mainImageUri")
        val petImageUrl: String,
    )
}
