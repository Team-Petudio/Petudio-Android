package com.composition.damoa.data.mapper

import com.composition.damoa.data.model.ProfileConceptDetail
import com.composition.damoa.data.network.dto.response.ConceptDetailResponse

fun ConceptDetailResponse.toDomain(): ProfileConceptDetail = ProfileConceptDetail(
    successImageUrls = successImageUrls,
    failImageUrls = failImageUrls,
)
