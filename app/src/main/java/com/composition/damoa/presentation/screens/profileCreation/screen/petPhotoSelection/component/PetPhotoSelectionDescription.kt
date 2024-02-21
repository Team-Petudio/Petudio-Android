package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.MediumDescription


@Composable
fun PetPhotoSelectionDescription() {
    MediumDescription(
        modifier = Modifier.padding(bottom = 20.dp),
        descriptionRes = R.string.pet_photo_select_desc,
    )
}