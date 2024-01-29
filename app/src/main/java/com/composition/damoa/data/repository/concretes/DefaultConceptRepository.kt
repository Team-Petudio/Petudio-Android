package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.mapper.toDomain
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.service.ConceptService
import com.composition.damoa.presentation.screens.home.ProfileConcept

class DefaultConceptRepository(
    private val conceptService: ConceptService,
) : ConceptRepository {

    override suspend fun getConcepts(): ApiResponse<List<ProfileConcept>> = conceptService
        .getConcepts()
        .map { conceptsResponse -> conceptsResponse.data.toDomain() }
}