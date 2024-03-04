package com.composition.damoa.data.network.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleLoginRequest(
    @SerialName("grant_type")
    private val grantType: String,
    @SerialName("client_id")
    private val clientId: String,
    @SerialName("client_secret")
    private val clientSecret: String,
    @SerialName("code")
    private val code: String,
)
