package com.composition.damoa.presentation.screens.profileCreation.screen.petColor.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.composition.damoa.presentation.common.components.SmallDescription
import com.composition.damoa.presentation.common.components.TinyTitle
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Purple60


@Composable
fun PetColorButton(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    @StringRes extraTitleRes: Int? = null,
    @DrawableRes petColorImageRes: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit,
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