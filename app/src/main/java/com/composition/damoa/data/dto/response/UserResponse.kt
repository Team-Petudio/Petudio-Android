package com.composition.damoa.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val socialType: SocialType,
    val email: String,
    val notificationStatus: Boolean,
    val pointAmount: Int,
) {

    enum class SocialType {
        @SerialName("GOOGLE")
        GOOGLE,

        @SerialName("KAKAO")
        KAKAO,

        @SerialName("APPLE")
        APPLE,
    }
}