package com.composition.damoa.presentation.screens.profileCreation.screen.petName.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.MediumDescription


@Composable
fun PetNameDescription(modifier: Modifier = Modifier) {
    MediumDescription(
        modifier = modifier.fillMaxWidth(),
        descriptionRes = R.string.pet_name_desc,
    )
}