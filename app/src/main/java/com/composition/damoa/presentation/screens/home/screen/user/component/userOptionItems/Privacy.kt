package com.composition.damoa.presentation.screens.home.screen.user.component.userOptionItems

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigDescription


@Composable
fun Privacy(onClick: () -> Unit) {
    UserOptionItem(
        item = {
            BigDescription(
                descriptionRes = R.string.profile_option_privacy,
                fontColor = Color.Black,
            )
        },
        onClick = onClick,
    )
}