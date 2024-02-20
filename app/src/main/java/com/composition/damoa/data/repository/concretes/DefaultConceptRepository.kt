package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.mapper.toDomain
import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.data.model.ProfileConceptDetail
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.service.ConceptService

class DefaultConceptRepository(
    private val conceptService: ConceptService,
) : ConceptRepository {

    override suspend fun getProfileConcepts(): ApiResponse<List<ProfileConcept>> = conceptService
        .getConcepts()
        .map { response -> response.data.toDomain() }

    override suspend fun getProfileConcept(conceptId: Long): ApiResponse<ProfileConcept> = conceptService
        .getConcepts()
        .map { response ->
            response.data.toDomain().first { it.id == conceptId }
        }

    override suspend fun getProfileConceptDetail(conceptId: Long): ApiResponse<ProfileConceptDetail> = conceptService
        .getConceptDetail(conceptId)
        .map { response -> response.data.toDomain() }
}
