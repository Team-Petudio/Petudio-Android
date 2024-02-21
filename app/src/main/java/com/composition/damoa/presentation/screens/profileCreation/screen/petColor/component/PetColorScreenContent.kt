package com.composition.damoa.presentation.screens.profileCreation.screen.petColor.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.MediumDescription


@Composable
fun PetColorScreenContent(
    modifier: Modifier = Modifier,
    petType: PetType,
    selectedPetColor: PetColor? = null,
    onPetColorSelected: (PetColor) -> Unit,
) {
    Column(modifier) {
        BigTitle(
            titleRes = R.string.pet_color_title,
            modifier = Modifier.padding(top = 20.dp),
        )
        MediumDescription(
            descriptionRes = R.string.pet_color_desc,
            modifier = Modifier.padding(top = 12.dp),
        )
        PetColorButtons(
            petType = petType,
            modifier = Modifier.padding(top = 28.dp),
            selectedPetColor = selectedPetColor,
            onPetColorSelected = onPetColorSelected
        )
    }
}