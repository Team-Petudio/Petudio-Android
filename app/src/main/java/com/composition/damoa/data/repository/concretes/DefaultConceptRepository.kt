package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.mapper.toDomain
import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.data.model.ProfileConceptDetail
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.service.ConceptService
import kotlinx.collections.immutable.PersistentList

class DefaultConceptRepository(
    private val service: ConceptService,
) : ConceptRepository {

    override suspend fun getProfileConcepts(): ApiResponse<PersistentList<ProfileConcept>> = service
        .getConcepts()
        .map { response -> response.data.toDomain() }

    override suspend fun getProfileConcept(conceptId: Long): ApiResponse<ProfileConcept> = service
        .getConcepts()
        .map { response ->
            response.data.toDomain().first { it.id == conceptId }
        }

    override suspend fun getProfileConceptDetail(conceptId: Long): ApiResponse<ProfileConceptDetail> = service
        .getConceptDetail(conceptId)
        .map { response -> response.data.toDomain() }
}
