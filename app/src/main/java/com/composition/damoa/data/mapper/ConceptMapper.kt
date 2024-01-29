package com.composition.damoa.data.mapper

import com.composition.damoa.data.dto.response.ConceptResponse
import com.composition.damoa.data.dto.response.ConceptsResponse
import com.composition.damoa.presentation.screens.home.ProfileConcept

fun ConceptsResponse.toDomain(): List<ProfileConcept> = concepts.map { conceptResponse ->
    ProfileConcept(
        id = conceptResponse.id,
        conceptName = conceptResponse.name,
        conceptDescription = conceptResponse.description,
        conceptImageUrl = conceptResponse.thumbnailUrl,
        isNewConcept = conceptResponse.isNew,
        animalType = conceptResponse.animalType.toDomain(),
    )
}

private fun ConceptResponse.AnimalType.toDomain(): ProfileConcept.AnimalType = when (this) {
    ConceptResponse.AnimalType.DOG -> ProfileConcept.AnimalType.DOG
    ConceptResponse.AnimalType.CAT -> ProfileConcept.AnimalType.CAT
}
