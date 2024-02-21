package com.composition.damoa.presentation.screens.profileCreation.screen.petName.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.SmallTitle


@Composable
fun PetNameInputTitle(modifier: Modifier = Modifier) {
    SmallTitle(
        modifier = modifier.fillMaxWidth(),
        titleRes = R.string.pet_name,
    )
}