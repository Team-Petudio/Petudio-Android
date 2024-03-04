package com.composition.damoa.data.mapper

import com.composition.damoa.data.model.PetType
import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.data.network.dto.response.ConceptResponse
import com.composition.damoa.data.network.dto.response.ConceptsResponse
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

fun ConceptsResponse.toDomain(): PersistentList<ProfileConcept> = concepts.map { conceptResponse ->
    ProfileConcept(
        id = conceptResponse.id,
        conceptName = conceptResponse.name,
        conceptDescription = conceptResponse.description,
        conceptImageUrl = conceptResponse.thumbnailUrl,
        isNewConcept = conceptResponse.isNew,
        petType = conceptResponse.animalType.toDomain(),
    )
}.toPersistentList()

private fun ConceptResponse.AnimalType.toDomain(): PetType = when (this) {
    ConceptResponse.AnimalType.DOG -> PetType.DOG
    ConceptResponse.AnimalType.CAT -> PetType.CAT
}
