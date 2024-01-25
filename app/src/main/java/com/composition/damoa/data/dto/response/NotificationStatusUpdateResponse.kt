package com.composition.damoa.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationStatusUpdateResponse(
    @SerialName("notificationStatus")
    val notificationStatus: Boolean,
)