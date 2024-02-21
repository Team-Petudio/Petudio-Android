package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoUpload.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PetPhoto(
    @DrawableRes val imageRes: Int,
    @StringRes val descRes: Int,
) {
    enum class PhotoType {
        GOOD_EXAMPLE,
        BAD_EXAMPLE,
    }
}