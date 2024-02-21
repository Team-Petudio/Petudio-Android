package com.composition.damoa.presentation.screens.profileCreation.screen.petName.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Gray40
import com.composition.damoa.presentation.ui.theme.Purple60


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PetNameInput(
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
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
