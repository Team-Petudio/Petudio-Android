package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.PetType
import java.io.File

interface PetDetectRepository {
    suspend fun detectPet(
        imageFiles: List<File>,
        petType: PetType,
    ): ApiResponse<List<Boolean>>
}