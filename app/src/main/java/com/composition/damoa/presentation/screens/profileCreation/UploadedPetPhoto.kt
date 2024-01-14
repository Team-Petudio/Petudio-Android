package com.composition.damoa.presentation.screens.profileCreation

import java.time.LocalDateTime

data class UploadedPetPhoto(
    val thumbnailUrl: String,
    val petName: String,
    val uploadedDate: LocalDateTime,
)
