package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.presentation.screens.home.ProfileConcept

interface ConceptRepository {
    suspend fun getConcepts(): ApiResponse<List<ProfileConcept>>
}