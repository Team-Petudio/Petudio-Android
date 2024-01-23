package com.composition.damoa.data.dto.request

import com.composition.damoa.data.model.Account
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("socialType")
    val socialType: Account.SocialType,
    @SerialName("token")
    val accessToken: String,
    @SerialName("fcmToken")
    val fcmToken: String,
)
