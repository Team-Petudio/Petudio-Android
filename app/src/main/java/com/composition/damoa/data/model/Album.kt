package com.composition.damoa.data.model

import kotlinx.collections.immutable.PersistentList
import java.time.LocalDateTime

data class Album(
    val id: Long,
    val title: String,
    val concept: String,
    val thumbnailUrl: String,
    val photoUrls: PersistentList<String>,
    val date: LocalDateTime,
)