package com.composition.damoa.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class User(
    val id: Long,
    val email: String,
    val socialType: SocialType,
    val ticketCount: Int,
    val token: Token = Token(),
) {
    enum class SocialType {
        @SerialName("GOOGLE")
        GOOGLE,

        @SerialName("KAKAO")
        KAKAO,

        @SerialName("APPLE")
        APPLE,
    }

    @Serializable
    data class Token(
        val accessToken: String = "",
        val refreshToken: String = "",
    )
}