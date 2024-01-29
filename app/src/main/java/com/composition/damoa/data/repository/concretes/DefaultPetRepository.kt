package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.mapper.toDomain
import com.composition.damoa.data.repository.interfaces.PetRepository
import com.composition.damoa.data.service.PetService
import com.composition.damoa.presentation.screens.profileCreation.Pet

class DefaultPetRepository(
    private val service: PetService,
) : PetRepository {
    override suspend fun getPets(): ApiResponse<List<Pet>> = service
        .getPets()
        .map { petResponse -> petResponse.data.toDomain() }
}