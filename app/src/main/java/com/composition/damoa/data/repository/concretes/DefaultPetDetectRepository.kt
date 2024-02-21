package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.PetType
import com.composition.damoa.data.repository.interfaces.PetDetectRepository
import com.composition.damoa.data.service.PetDetectService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class DefaultPetDetectRepository(
    private val service: PetDetectService,
) : PetDetectRepository {

    override suspend fun detectPet(
        imageFiles: List<File>,
        petType: PetType,
    ): ApiResponse<List<Boolean>> {
        val imageMultiParts = imageFiles.map { imageFile ->
            MultipartBody.Part.createFormData(
                "files",
                imageFile.name,
                imageFile.asRequestBody("image/*".toMediaTypeOrNull()),
            )
        }

        return service.detectPet(
            petType = petType.name,
            petImages = imageMultiParts,
        ).map { response -> response.detectResults }
    }
}