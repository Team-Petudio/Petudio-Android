package com.composition.damoa.presentation.screens.home.screen.user.component

import androidx.compose.runtime.Composable
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.PetudioDialog


@Composable
fun LogoutDialog(
    onDismissClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    PetudioDialog(
        dialogTitleRes = R.string.logout,
        dialogDescRes = R.string.logout_desc,
        onDismissClick = onDismissClick,
        onConfirmClick = onLogoutClick,
    )
}