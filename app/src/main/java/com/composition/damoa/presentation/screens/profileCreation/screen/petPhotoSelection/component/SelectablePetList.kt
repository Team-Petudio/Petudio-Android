package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.data.model.Pet


@Composable
fun SelectablePetList(
    modifier: Modifier = Modifier,
    pets: List<Pet>,
    selectedPetId: Long? = null,
    onPetSelected: (petId: Long) -> Unit,
    onNewPhotoUploadClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier.selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 100.dp),
    ) {
        item {
            PetPhotoSelectionTitle()
            PetPhotoSelectionDescription()
            NewPhotoUploadButton(onClick = onNewPhotoUploadClick)
        }
        items(
            items = pets,
            key = { pet -> pet.id },
        ) { pet ->
            SelectablePetItem(
                pet = pet,
                onClick = { onPetSelected(pet.id) },
                selected = pet.id == selectedPetId,
            )
        }
    }
}