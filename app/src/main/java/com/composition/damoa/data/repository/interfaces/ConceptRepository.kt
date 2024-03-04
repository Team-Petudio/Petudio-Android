package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.model.ProfileConcept
import com.composition.damoa.data.model.ProfileConceptDetail
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import kotlinx.collections.immutable.PersistentList

interface ConceptRepository {
    suspend fun getProfileConcepts(): ApiResponse<PersistentList<ProfileConcept>>

    suspend fun getProfileConcept(conceptId: Long): ApiResponse<ProfileConcept>

    suspend fun getProfileConceptDetail(conceptId: Long): ApiResponse<ProfileConceptDetail>
}