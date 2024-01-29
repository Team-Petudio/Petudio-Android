package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.Pet

interface PetRepository {
    suspend fun getPets(): ApiResponse<List<Pet>>
}