package com.composition.damoa.presentation.screens.profileCreation.screen.creationIntroduce.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.MediumTitle


@Composable
fun ProfileCreationIntroduceTitle(
    modifier: Modifier = Modifier,
) {
    MediumTitle(
        modifier = modifier,
        titleRes = R.string.profile_create_introduce_title,
    )
}