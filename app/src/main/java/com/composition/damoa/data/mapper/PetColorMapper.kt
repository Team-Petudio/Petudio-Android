package com.composition.damoa.data.mapper

import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.network.dto.request.PetRequest

fun PetColor.toData(): PetRequest.PetColor = when (this) {
    PetColor.WHITE -> PetRequest.PetColor.WHITE
    PetColor.BLACK -> PetRequest.PetColor.BLACK
    PetColor.DYNAMIC -> PetRequest.PetColor.DYNAMIC
}