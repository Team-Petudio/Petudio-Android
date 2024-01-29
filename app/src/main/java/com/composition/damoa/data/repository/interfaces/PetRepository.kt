package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.presentation.screens.profileCreation.Pet

interface PetRepository {
    suspend fun getPets(): ApiResponse<List<Pet>>
}