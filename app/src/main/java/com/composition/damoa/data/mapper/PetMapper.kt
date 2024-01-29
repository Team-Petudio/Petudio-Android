package com.composition.damoa.data.mapper

import com.composition.damoa.data.dto.response.PetsResponse
import com.composition.damoa.data.model.Pet

fun PetsResponse.toDomain(): List<Pet> = pets
    .map { pets -> pets.toDomain() }

private fun PetsResponse.PetResponse.toDomain(): Pet = Pet(
    id = id,
    petName = name,
    thumbnailUrl = thumbnailUrl,
)