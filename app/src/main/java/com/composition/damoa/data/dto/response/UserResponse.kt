package com.composition.damoa.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("memberId")
    val id: Long,
    @SerialName("socialType")
    val socialType: SocialType,
    @SerialName("email")
    val email: String,
    @SerialName("notificationStatus")
    val notificationStatus: Boolean,
    @SerialName("ticketAmount")
    val ticketCount: Int,
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