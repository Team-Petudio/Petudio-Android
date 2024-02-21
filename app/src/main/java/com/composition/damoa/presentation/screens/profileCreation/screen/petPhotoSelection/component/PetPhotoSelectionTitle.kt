package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.MediumTitle


@Composable
fun PetPhotoSelectionTitle() {
    MediumTitle(
        modifier = Modifier.padding(top = 12.dp, bottom = 6.dp),
        titleRes = R.string.pet_photo_select_title,
    )
}