package com.composition.damoa.data.mapper

import com.composition.damoa.data.model.PetFeed
import com.composition.damoa.data.network.dto.response.PetFeedResponse
import com.composition.damoa.data.network.dto.response.PetFeedsResponse

fun PetFeedsResponse.toDomain(): List<PetFeed> = petFeeds.map { it.toDomain() }

private fun PetFeedResponse.toDomain(): PetFeed = PetFeed(
    id = feedId,
    title = petName,
    concept = conceptName,
    likeCount = likeCount,
    isLike = isLiked,
    thumbnailUrl = profileImageUri,
)