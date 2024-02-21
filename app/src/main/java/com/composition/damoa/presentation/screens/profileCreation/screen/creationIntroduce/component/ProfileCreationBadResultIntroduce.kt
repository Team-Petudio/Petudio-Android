package com.composition.damoa.presentation.screens.profileCreation.screen.creationIntroduce.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R


@Composable
fun ProfileCreationBadResultIntroduce(
    modifier: Modifier = Modifier,
) {
    ProfileCreationIntroduce(
        modifier = modifier,
        isShowAlertIcon = true,
        titleRes = R.string.profile_create_bad_example_intro_title,
        descriptionRes = R.string.profile_create_bad_example_intro_desc,
    )
}