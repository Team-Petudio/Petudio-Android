package com.composition.damoa.data.model

import kotlinx.serialization.SerialName

data class Account(
    val email: String,
    val socialType: SocialType,
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