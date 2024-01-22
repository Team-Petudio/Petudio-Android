package com.composition.damoa.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleLoginResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("scope")
    val scope: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("id_token")
    val idToken: String,
)