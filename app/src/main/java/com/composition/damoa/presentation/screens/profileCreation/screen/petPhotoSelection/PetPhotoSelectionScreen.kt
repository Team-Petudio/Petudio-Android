package com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.composition.damoa.R
import com.composition.damoa.data.model.Pet
import com.composition.damoa.presentation.common.components.KeepGoingButton
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.screens.profileCreation.component.ProfileCreationScreen
import com.composition.damoa.presentation.screens.profileCreation.screen.petPhotoSelection.state.PetPhotoSelectionUiState
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Purple60

@Composable
fun PetPhotoSelectionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    petPhotoSelectionUiState: PetPhotoSelectionUiState,
) {
    Surface(
        color = Color.White,
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        PetPhotoSelectContent(
            pets = petPhotoSelectionUiState.pets,
            selectedPetId = petPhotoSelectionUiState.selectedPetId,
            onPetSelected = { petId -> petPhotoSelectionUiState.onPetSelected(petId) },
            onNewPhotoUploadClick = { navController.navigate(ProfileCreationScreen.PET_NAME.route) }
        )
        KeepGoingButton(
            enabled = petPhotoSelectionUiState.selectedPetId != null,
            onClick = { navController.navigate(ProfileCreationScreen.PAYMENT.route) }
        )
    }
}

@Composable
private fun PetPhotoSelectContent(
    pets: List<Pet>,
    selectedPetId: Long? = null,
    onPetSelected: (petId: Long) -> Unit,
    onNewPhotoUploadClick: () -> Unit,
) {
    PetList(
        pets = pets,
        selectedPetId = selectedPetId,
        onPetSelected = onPetSelected,
        onNewPhotoUploadClick = onNewPhotoUploadClick,
    )
}

@Composable
private fun PetPhotoSelectTitle() {
    MediumTitle(
        modifier = Modifier.padding(top = 12.dp, bottom = 6.dp),
        titleRes = R.string.pet_photo_select_title,
    )
}

@Composable
private fun PetPhotoSelectDescription() {
    MediumDescription(
        modifier = Modifier.padding(bottom = 20.dp),
        descriptionRes = R.string.pet_photo_select_desc,
    )
}

@Composable
private fun NewPhotoUploadButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, Gray20),
        onClick = onClick,
    ) {
        Row(
            Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.padding(end = 4.dp),
            )
            Text(
                text = stringResource(id = R.string.pet_photo_new_upload),
                color = Color.Black,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun PetList(
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
            PetPhotoSelectTitle()
            PetPhotoSelectDescription()
            NewPhotoUploadButton(onClick = onNewPhotoUploadClick)
        }
        items(
            items = pets,
            key = { pet -> pet.id },
        ) { pet ->
            PetItem(
                pet = pet,
                onClick = { onPetSelected(pet.id) },
                selected = pet.id == selectedPetId,
            )
        }
    }
}

@Composable
private fun PetItem(
    modifier: Modifier = Modifier,
    pet: Pet,
    onClick: () -> Unit = {},
    selected: Boolean = false,
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
        UploadedPhotoThumbnail(thumbnailUrl = pet.thumbnailUrl)
        PetInformation(pet = pet)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun UploadedPhotoThumbnail(
    modifier: Modifier = Modifier,
    thumbnailUrl: String,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp,
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(1F),
    ) {
        GlideImage(
            model = thumbnailUrl,
            contentDescription = null,
            transition = CrossFade,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun PetInformation(
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
