package com.composition.damoa.presentation.screens.home.screen.user.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.PetudioDialog
import com.composition.damoa.presentation.ui.theme.AlertIconColor


@Composable
fun SignOutDialog(
    onDismissClick: () -> Unit,
    onSignOutClick: () -> Unit,
) {
    PetudioDialog(
        painter = painterResource(R.drawable.alert),
        iconColor = AlertIconColor,
        dialogTitleRes = R.string.sign_out,
        dialogDescRes = R.string.sign_out_desc,
        onDismissClick = onDismissClick,
        onConfirmClick = onSignOutClick,
    )
}
