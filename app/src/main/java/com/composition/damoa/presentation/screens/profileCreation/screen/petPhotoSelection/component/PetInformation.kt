package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.composition.damoa.data.model.Pet


@Composable
fun PetInformation(
    modifier: Modifier = Modifier,
    pet: Pet,
) {
    Text(
        modifier = modifier,
        text = pet.petName,
        color = Color.Black,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
    )
}