package com.composition.damoa.data.mapper

import com.composition.damoa.data.model.Pet
import com.composition.damoa.data.network.dto.response.PetsResponse

fun PetsResponse.toDomain(): List<Pet> = pets
    .map { pets -> pets.toDomain() }

private fun PetsResponse.PetResponse.toDomain(): Pet = Pet(
    id = id,
    petName = name,
    petImageUrl = petImageUrl,
)