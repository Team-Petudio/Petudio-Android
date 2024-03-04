package com.composition.damoa.data.network.dto.request

import com.composition.damoa.data.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("socialType")
    val socialType: User.SocialType,
    @SerialName("token")
    val accessToken: String,
    @SerialName("fcmToken")
    val fcmToken: String,
)
