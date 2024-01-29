package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.data.model.ProfileConceptDetail

interface ConceptRepository {
    suspend fun getConcepts(): ApiResponse<List<ProfileConcept>>

    suspend fun getConceptDetail(conceptId: Long): ApiResponse<ProfileConceptDetail>
}