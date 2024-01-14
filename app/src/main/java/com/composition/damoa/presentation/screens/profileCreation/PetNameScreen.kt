package com.composition.damoa.presentation.screens.profileCreation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.KeepGoingButton
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.SmallTitle
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Gray40
import com.composition.damoa.presentation.ui.theme.Purple60

@Composable
fun PetNameScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Surface(
        color = Color.White,
        modifier =
            modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
    ) {
        var petName by remember { mutableStateOf("") }

        Column {
            PetNameTitle(modifier = Modifier.padding(top = 20.dp, bottom = 4.dp))
            PetNameDescription()
            PetNameInputTitle(modifier = Modifier.padding(top = 30.dp))
            PetNameInput(
                name = petName,
                onNameChanged = { newPetName -> petName = newPetName },
                onNext = { navigateToPetColorScreen(navController) },
                modifier = Modifier.padding(top = 12.dp),
            )
        }
        KeepGoingButton(
            onClick = { navigateToPetColorScreen(navController) },
            enabled = petName.isNotEmpty(),
        )
    }
}

private fun navigateToPetColorScreen(navController: NavController) {
    navController.navigate(ProfileCreationScreen.PET_COLOR.route)
}

@Composable
private fun PetNameTitle(modifier: Modifier = Modifier) {
    BigTitle(
        titleRes = R.string.pet_name_title,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun PetNameDescription(modifier: Modifier = Modifier) {
    MediumDescription(
        modifier = modifier.fillMaxWidth(),
        descriptionRes = R.string.pet_name_desc,
    )
}

@Composable
private fun PetNameInputTitle(modifier: Modifier = Modifier) {
    SmallTitle(
        modifier = modifier.fillMaxWidth(),
        titleRes = R.string.pet_name,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PetNameInput(
    modifier: Modifier = Modifier,
    name: String,
    onNameChanged: (String) -> Unit,
    onNext: () -> Unit,
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChanged,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth(),
        placeholder = { PetNameHint() },
        singleLine = true,
        keyboardActions = KeyboardActions(onNext = { onNext() }),
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
        colors =
            TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Purple60,
                unfocusedBorderColor = Gray20,
            ),
    )
}

@Composable
private fun PetNameHint() {
    Text(
        text = stringResource(R.string.pet_name_input_hint),
        color = Gray40,
    )
}

@Preview
@Composable
private fun PetNameScreenPreview() {
    PetNameScreen(navController = rememberNavController())
}
