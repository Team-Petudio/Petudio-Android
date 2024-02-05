package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import java.io.File

interface S3ImageRepository {
    suspend fun uploadImage(
        directoryPath: String,
        imageFile: File,
    ): ApiResponse<Unit>
}
