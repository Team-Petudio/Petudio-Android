package com.composition.damoa.data.dto.request

import com.composition.damoa.data.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReissueTokenRequest(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
) {
    constructor(token: User.Token) : this(
        accessToken = token.accessToken,
        refreshToken = token.refreshToken,
    )
}
