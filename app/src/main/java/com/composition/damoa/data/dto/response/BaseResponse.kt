package com.composition.damoa.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val code: String,
    val message: String,
    val data: T,
    val success: Boolean,
)