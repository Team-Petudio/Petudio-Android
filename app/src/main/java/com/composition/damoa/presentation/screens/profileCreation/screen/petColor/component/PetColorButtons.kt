package com.composition.damoa.presentation.screens.profileCreation.screen.petColor.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.model.PetType


@Composable
fun PetColorButtons(
    modifier: Modifier = Modifier,
    petType: PetType,
    selectedPetColor: PetColor? = null,
    onPetColorSelected: (PetColor) -> Unit,
) {
    val (white, black, dynamicColor) = when (petType) {
        PetType.DOG -> listOf(
            R.drawable.img_white_dogs,
            R.drawable.img_black_dogs,
            R.drawable.img_dynamic_color_dogs,
        )

        PetType.CAT -> listOf(
            R.drawable.img_white_cats,
            R.drawable.img_black_cats,
            R.drawable.img_dynamic_color_cats,
        )
    }

    Column(modifier) {
        Row {
            PetColorButton(
                titleRes = R.string.pet_color_white,
                petColorImageRes = white,
                modifier = Modifier.weight(1F),
                isSelected = selectedPetColor == PetColor.WHITE,
                onClick = { onPetColorSelected(PetColor.WHITE) },
            )
            Spacer(modifier = Modifier.size(8.dp))
            PetColorButton(
                titleRes = R.string.pet_color_black,
                petColorImageRes = black,
                modifier = Modifier.weight(1F),
                isSelected = selectedPetColor == PetColor.BLACK,
                onClick = { onPetColorSelected(PetColor.BLACK) },
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        PetColorButton(
            titleRes = R.string.pet_color_dynamic,
            extraTitleRes = R.string.pet_color_dynamic_extra_desc,
            petColorImageRes = dynamicColor,
            isSelected = selectedPetColor == PetColor.DYNAMIC,
            onClick = { onPetColorSelected(PetColor.DYNAMIC) },
        )
    }
}