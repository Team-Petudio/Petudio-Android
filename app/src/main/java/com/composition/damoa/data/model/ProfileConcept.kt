package com.composition.damoa.data.model

data class ProfileConcept(
    val id: Long,
    val conceptName: String,
    val conceptDescription: String,
    val conceptImageUrl: String,
    val petType: PetType,
    val isNewConcept: Boolean,
    val conceptPrice: Int,
    val discountedPrice: Int,
)
