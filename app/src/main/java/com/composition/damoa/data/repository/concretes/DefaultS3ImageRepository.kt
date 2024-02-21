package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.repository.interfaces.S3ImageRepository
import com.composition.damoa.data.service.S3ImageService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class DefaultS3ImageRepository(
    private val service: S3ImageService,
) : S3ImageRepository {

    override suspend fun uploadImage(
        directoryPath: String,
        imageFile: File,
    ): ApiResponse<Unit> {
        val multipart = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            imageFile.asRequestBody("image/*".toMediaTypeOrNull()),
        )
        return service.uploadImage(directoryPath, multipart)
    }
}