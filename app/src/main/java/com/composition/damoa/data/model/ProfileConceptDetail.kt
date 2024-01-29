package com.composition.damoa.data.model

data class ProfileConceptDetail(
    val successImageUrls: List<String> = emptyList(),
    val failImageUrls: List<String> = emptyList(),
    val conceptPoint: Int = Int.MAX_VALUE,
)