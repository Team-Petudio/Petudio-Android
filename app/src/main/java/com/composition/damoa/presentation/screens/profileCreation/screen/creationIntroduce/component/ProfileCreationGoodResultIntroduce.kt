package com.composition.damoa.presentation.screens.profileCreation.screen.creationIntroduce.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R


@Composable
fun ProfileCreationGoodResultIntroduce(
    modifier: Modifier = Modifier,
) {
    ProfileCreationIntroduce(
        modifier = modifier,
        titleRes = R.string.profile_create_good_example_intro_title,
        descriptionRes = R.string.profile_create_good_example_intro_desc,
    )
}