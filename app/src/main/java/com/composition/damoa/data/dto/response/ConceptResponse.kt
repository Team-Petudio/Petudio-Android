package com.composition.damoa.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConceptsResponse(
    @SerialName("conceptInfos")
    val concepts: List<ConceptResponse>,
)

@Serializable
data class ConceptResponse(
    @SerialName("conceptId")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("descriptionMessage")
    val description: String,
    @SerialName("mainImageUri")
    val thumbnailUrl: String,
    @SerialName("isNew")
    val isNew: Boolean,
    @SerialName("petType")
    val animalType: AnimalType,
) {
    enum class AnimalType {
        @SerialName("DOG")
        DOG,

        @SerialName("CAT")
        CAT
    }
}