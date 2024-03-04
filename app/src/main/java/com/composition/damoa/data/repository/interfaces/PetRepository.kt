package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.model.Pet
import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse

interface PetRepository {
    suspend fun getPets(): ApiResponse<List<Pet>>

    suspend fun uploadPet(
        petName: String,
        petColor: PetColor,
        petPhotoUrls: List<String>,
    ): ApiResponse<Unit>
}