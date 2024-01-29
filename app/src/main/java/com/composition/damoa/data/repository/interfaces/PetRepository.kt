package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.Pet
import com.composition.damoa.data.model.PetColor

interface PetRepository {
    suspend fun getPets(): ApiResponse<List<Pet>>

    suspend fun addPet(
        petName: String,
        petColor: PetColor,
        petPhotoUrls: List<String>,
    ): ApiResponse<Unit>
}