package com.composition.damoa.presentation.screens.profileCreation.screen.petColor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.common.components.KeepGoingButton
import com.composition.damoa.presentation.screens.profileCreation.screen.petColor.component.PetColorScreenContent
import com.composition.damoa.presentation.screens.profileCreation.state.PetInfoUiState


@Composable
fun PetColorScreen(
    modifier: Modifier = Modifier,
    petType: PetType = PetType.DOG,
    petInfoUiState: PetInfoUiState,
    onKeepGoingClick: () -> Unit,
) {
    Surface(
        color = Color.White,
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        PetColorScreenContent(
            petType = petType,
            selectedPetColor = petInfoUiState.petColor,
            onPetColorSelected = petInfoUiState.onPetColorSelected,
        )
        KeepGoingButton(
            enabled = petInfoUiState.petColor != null,
            onClick = onKeepGoingClick,
        )
    }
}
