package com.composition.damoa.data.model

import java.time.LocalDateTime

data class Album(
    val id: Long,
    val title: String,
    val concept: String,
    val thumbnailUrl: String,
    val photoUrls: List<String>,
    val date: LocalDateTime,
)