package com.composition.damoa.data.model

import kotlinx.serialization.SerialName

data class User(
    val email: String,
    val socialType: SocialType,
    val ticket: Int,
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

    data class Token(
        val accessToken: String = "",
        val refreshToken: String = "",
    )
}