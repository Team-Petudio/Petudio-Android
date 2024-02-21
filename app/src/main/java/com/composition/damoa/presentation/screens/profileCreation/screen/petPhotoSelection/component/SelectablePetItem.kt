package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.composition.damoa.data.model.Pet
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Purple60


@Composable
fun SelectablePetItem(
    modifier: Modifier = Modifier,
    pet: Pet,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val borderWidth by animateDpAsState(
        targetValue = if (selected) 3.dp else 1.5.dp,
        label = "pet item border width",
    )
    val borderColor by animateColorAsState(
        targetValue = if (selected) Purple60 else Gray20,
        label = "pet item border color",
    )
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .selectable(
                selected = true,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
                role = Role.RadioButton,
            )
            .fillMaxWidth()
            .aspectRatio(10 / 4F)
            .border(borderWidth, borderColor, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PetThumbnail(thumbnailUrl = pet.thumbnailUrl)
        PetInformation(pet = pet)
    }
}