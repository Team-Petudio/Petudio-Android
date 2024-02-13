package com.composition.damoa.presentation.screens.profileCreation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composition.damoa.R
import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.KeepGoingButton
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.SmallDescription
import com.composition.damoa.presentation.common.components.TinyTitle
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Purple60

@Composable
fun PetColorScreen(
    modifier: Modifier = Modifier,
    petType: PetType = PetType.DOG,
    selectedPetColor: PetColor? = null,
    onPetColorSelected: (PetColor) -> Unit,
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
            selectedPetColor = selectedPetColor,
            onPetColorSelected = onPetColorSelected,
        )
        KeepGoingButton(onClick = onKeepGoingClick)
    }
}

@Composable
private fun PetColorScreenContent(
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

@Composable
private fun PetColorButtons(
    modifier: Modifier = Modifier,
    petType: PetType,
    selectedPetColor: PetColor? = null,
    onPetColorSelected: (PetColor) -> Unit,
) {
    val (white, black, dynamicColor) = when (petType) {
        PetType.DOG -> Triple(
            R.drawable.img_white_dogs,
            R.drawable.img_black_dogs,
            R.drawable.img_dynamic_color_dogs,
        )

        PetType.CAT -> Triple(
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

@Composable
private fun PetColorButton(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    @StringRes extraTitleRes: Int? = null,
    @DrawableRes petColorImageRes: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            if (isSelected) 2.dp else 1.5.dp,
            if (isSelected) Purple60 else Gray20,
        ),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(20.dp),
        colors = ButtonDefaults.outlinedButtonColors(),
    ) {
        Column {
            Row {
                TinyTitle(titleRes = titleRes)
                Spacer(modifier = Modifier.size(6.dp))
                if (extraTitleRes != null) SmallDescription(descriptionRes = extraTitleRes)
            }
            Image(
                painter = painterResource(id = petColorImageRes),
                contentDescription = null,
                alignment = Alignment.CenterStart,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .height(36.dp),
            )
        }
    }
}
