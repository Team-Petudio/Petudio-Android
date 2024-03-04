package com.composition.damoa.data.network.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PetRequest(
    @SerialName("name")
    val petName: String,
    @SerialName("furColor")
    val petColor: PetColor,
    @SerialName("petPhotoUris")
    val petPhotoUrls: List<String>,
) {
    enum class PetColor {
        @SerialName("WHITE")
        WHITE,

        @SerialName("BLACK")
        BLACK,

        @SerialName("DYNAMIC")
        DYNAMIC
    }
}
