package com.composition.damoa.data.model

data class PetFeed(
    val id: Long,
    val title: String,
    val concept: String,
    val likeCount: Int,
    val isLike: Boolean,
    val thumbnailUrl: String,
)