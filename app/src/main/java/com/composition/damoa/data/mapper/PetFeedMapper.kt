package com.composition.damoa.data.mapper

import com.composition.damoa.data.dto.response.PetFeedResponse
import com.composition.damoa.data.dto.response.PetFeedsResponse
import com.composition.damoa.data.model.PetFeed

fun PetFeedsResponse.toDomain(): List<PetFeed> = petFeeds.map { it.toDomain() }

private fun PetFeedResponse.toDomain(): PetFeed = PetFeed(
    id = feedId,
    title = petName,
    concept = conceptName,
    likeCount = likeCount,
    isLike = isLiked,
    thumbnailUrl = profileImageUri,
)