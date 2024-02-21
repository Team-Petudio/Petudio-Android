package com.composition.damoa.presentation.screens.profileCreation.screen.petName.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigTitle


@Composable
fun PetNameTitle(modifier: Modifier = Modifier) {
    BigTitle(
        titleRes = R.string.pet_name_title,
        modifier = modifier.fillMaxWidth(),
    )
}