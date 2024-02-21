package com.composition.damoa.presentation.screens.profileCreation.screen.petName

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composition.damoa.presentation.common.components.KeepGoingButton
import com.composition.damoa.presentation.screens.profileCreation.screen.petName.component.PetNameDescription
import com.composition.damoa.presentation.screens.profileCreation.screen.petName.component.PetNameInput
import com.composition.damoa.presentation.screens.profileCreation.screen.petName.component.PetNameInputTitle
import com.composition.damoa.presentation.screens.profileCreation.screen.petName.component.PetNameTitle
import com.composition.damoa.presentation.screens.profileCreation.state.PetInfoUiState


@Composable
fun PetNameScreen(
    modifier: Modifier = Modifier,
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
        Column {
            PetNameTitle(modifier = Modifier.padding(top = 20.dp, bottom = 4.dp))
            PetNameDescription()
            PetNameInputTitle(modifier = Modifier.padding(top = 30.dp))
            PetNameInput(
                name = petInfoUiState.petName,
                onNameChanged = petInfoUiState.onPetNameChanged,
                onNext = onKeepGoingClick,
                modifier = Modifier.padding(top = 12.dp),
            )
        }
        KeepGoingButton(
            onClick = onKeepGoingClick,
            enabled = petInfoUiState.petName.isNotEmpty(),
        )
    }
}
