package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.dto.request.PetRequest
import com.composition.damoa.data.mapper.toData
import com.composition.damoa.data.mapper.toDomain
import com.composition.damoa.data.model.Pet
import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.repository.interfaces.PetRepository
import com.composition.damoa.data.service.PetService

class DefaultPetRepository(
    private val service: PetService,
) : PetRepository {

    override suspend fun getPets(): ApiResponse<List<Pet>> = service
        .getPets()
        .map { petResponse -> petResponse.data.toDomain() }

    override suspend fun uploadPet(
        petName: String,
        petColor: PetColor,
        petPhotoUrls: List<String>,
    ): ApiResponse<Unit> = service.addPet(
        request = PetRequest(
            petName = petName,
            petColor = petColor.toData(),
            petPhotoUrls = petPhotoUrls,
        )
    )
}