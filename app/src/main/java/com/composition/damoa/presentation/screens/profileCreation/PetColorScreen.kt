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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
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
    navController: NavController,
    // TODO("이후에 ViewModel에서 가져와야 함")
    petType: PetType = PetType.DOG,
) {
    Surface(
        color = Color.White,
        modifier =
            modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
    ) {
        PetColorScreenContent(petType = petType)
        KeepGoingButton(onClick = {
            navController.navigate(ProfileCreationScreen.PHOTO_UPLOAD_INTRODUCE.route)
        })
    }
}

@Composable
private fun PetColorScreenContent(
    modifier: Modifier = Modifier,
    petType: PetType,
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
        )
    }
}

@Composable
private fun PetColorButtons(
    modifier: Modifier = Modifier,
    petType: PetType,
) {
    val (white, black, dynamicColor) =
        when (petType) {
            PetType.DOG ->
                Triple(
                    R.drawable.img_white_dogs,
                    R.drawable.img_black_dogs,
                    R.drawable.img_dynamic_color_dogs,
                )

            PetType.CAT ->
                Triple(
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
            )
            Spacer(modifier = Modifier.size(8.dp))
            PetColorButton(
                titleRes = R.string.pet_color_black,
                petColorImageRes = black,
                modifier = Modifier.weight(1F),
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        PetColorButton(
            titleRes = R.string.pet_color_dynamic,
            extraTitleRes = R.string.pet_color_dynamic_extra_desc,
            petColorImageRes = dynamicColor,
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
        border =
            BorderStroke(
                if (isSelected) 2.dp else 1.5.dp,
                if (isSelected) Purple60 else Gray20,
            ),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(20.dp),
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

@Preview
@Composable
private fun PetColorScreenPreview() {
    PetColorScreen(
        navController = rememberNavController(),
        petType = PetType.DOG,
    )
}
