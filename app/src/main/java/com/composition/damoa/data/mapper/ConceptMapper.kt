package com.composition.damoa.data.mapper

import com.composition.damoa.data.dto.response.ConceptResponse
import com.composition.damoa.data.dto.response.ConceptsResponse
import com.composition.damoa.data.model.PetType
import com.composition.damoa.data.model.ProfileConcept

fun ConceptsResponse.toDomain(): List<ProfileConcept> = concepts.map { conceptResponse ->
    ProfileConcept(
        id = conceptResponse.id,
        conceptName = conceptResponse.name,
        conceptDescription = conceptResponse.description,
        conceptImageUrl = conceptResponse.thumbnailUrl,
        isNewConcept = conceptResponse.isNew,
        petType = conceptResponse.animalType.toDomain(),
        conceptPoint = conceptResponse.conceptPrice,
        discountedPoint = conceptResponse.discountedPrice,
    )
}

private fun ConceptResponse.AnimalType.toDomain(): PetType = when (this) {
    ConceptResponse.AnimalType.DOG -> PetType.DOG
    ConceptResponse.AnimalType.CAT -> PetType.CAT
}
