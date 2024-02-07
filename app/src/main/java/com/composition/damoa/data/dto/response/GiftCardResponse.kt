package com.composition.damoa.data.dto.response

import com.composition.damoa.data.common.retrofit.serializer.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class GiftCardsResponse(
    @SerialName("giftsInfo")
    val giftCards: List<GiftCardResponse>,
)

@Serializable
data class GiftCardResponse(
    @SerialName("giftCode")
    val giftCode: String,
    @SerialName("expiredAt")
    @Serializable(with = LocalDateTimeSerializer::class)
    val expiredAt: LocalDateTime,
//    @SerialName("isExpired")
//    val isExpired: Boolean,
)