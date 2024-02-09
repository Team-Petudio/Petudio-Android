package com.composition.damoa.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PetFeedsResponse(
    @SerialName("postInfos")
    val petFeeds: List<PetFeedResponse>,
)

@Serializable
data class PetFeedResponse(
    @SerialName("feedId")
    val feedId: Long,
    @SerialName("memberId")
    val memberId: Long,
    @SerialName("petName")
    val petName: String,
    @SerialName("conceptName")
    val conceptName: String,
    @SerialName("profileImageUri")
    val profileImageUri: String,
    @SerialName("likeCount")
    val likeCount: Int,
    @SerialName("isLiked")
    val isLiked: Boolean,
)
