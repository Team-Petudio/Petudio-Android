package com.composition.damoa.data.mapper

import com.composition.damoa.data.dto.response.ConceptDetailResponse
import com.composition.damoa.data.model.ProfileConceptDetail

fun ConceptDetailResponse.toDomain(): ProfileConceptDetail = ProfileConceptDetail(
    successImageUrls = successImageUrls,
    failImageUrls = failImageUrls
)
