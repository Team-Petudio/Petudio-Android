package com.composition.damoa.presentation.screens.profileCreation.screen.creationIntroduce.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.SmallTitle


@Composable
fun ProfileCreationIntroduce(
    modifier: Modifier = Modifier,
    isShowAlertIcon: Boolean = false,
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int,
) {
    Column(modifier) {
        ProfileCreationIntroduceTitle(titleRes = titleRes, isShowAlertIcon = isShowAlertIcon)
        ProfileCreationIntroduceDescription(descriptionRes = descriptionRes)
    }
}

@Composable
private fun ProfileCreationIntroduceTitle(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    isShowAlertIcon: Boolean = false,
) {
    SmallTitle(
        modifier = modifier.padding(bottom = 12.dp),
        titleRes = titleRes,
        isShowAlertIcon = isShowAlertIcon,
    )
}

@Composable
private fun ProfileCreationIntroduceDescription(
    @StringRes descriptionRes: Int,
    modifier: Modifier = Modifier,
) {
    MediumDescription(
        modifier = modifier.padding(bottom = 16.dp),
        descriptionRes = descriptionRes,
    )
}