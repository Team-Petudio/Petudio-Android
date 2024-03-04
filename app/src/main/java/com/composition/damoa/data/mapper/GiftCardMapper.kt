package com.composition.damoa.data.mapper

import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.data.network.dto.response.GiftCardResponse
import com.composition.damoa.data.network.dto.response.GiftCardsResponse

fun GiftCardsResponse.toDomain(): List<GiftCard> = giftCards.map(GiftCardResponse::toDomain)

private fun GiftCardResponse.toDomain(): GiftCard = GiftCard(
    giftCode = giftCode,
    expiredAt = expiredAt,
    isExpired = isExpired,
    isUsed = isUsed,
)
