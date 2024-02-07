package com.composition.damoa.data.model

import java.time.LocalDateTime

data class GiftCard(
    val giftCode: String,
    val expiredAt: LocalDateTime,
    val isExpired: Boolean,
    val isUsed: Boolean,
)
