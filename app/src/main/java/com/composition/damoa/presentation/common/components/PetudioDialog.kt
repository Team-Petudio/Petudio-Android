package com.composition.damoa.presentation.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.Gray60
import com.composition.damoa.presentation.ui.theme.Purple80


@Composable
fun PetudioDialog(
    dialogIcon: Painter? = null,
    iconColor: Color = Color.Black,
    @StringRes dialogTitleRes: Int,
    @StringRes dialogDescRes: Int,
    @StringRes positiveTextRes: Int = R.string.confirm,
    @StringRes negativeTextRes: Int = R.string.cancel,
    onConfirmClick: () -> Unit = {},
    onDismissClick: () -> Unit = {},
) {
    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
        onDismissRequest = onDismissClick
    ) {
        PetudioDialogContent(
            dialogIcon = dialogIcon,
            iconColor = iconColor,
            title = stringResource(dialogTitleRes),
            message = stringResource(dialogDescRes),
            positiveText = stringResource(positiveTextRes),
            negativeText = stringResource(negativeTextRes),
            onClickOk = onConfirmClick,
            onClickNo = onDismissClick,
        )
    }
}

@Composable
private fun PetudioDialogContent(
    modifier: Modifier = Modifier,
    dialogIcon: Painter?,
    iconColor: Color,
    title: String,
    message: String,
    positiveText: String,
    negativeText: String,
    onClickOk: () -> Unit,
    onClickNo: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(40.dp)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (dialogIcon != null) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = dialogIcon, tint = iconColor,
                contentDescription = null,
            )
        }

        DialogTitle(title = title)

        if (message.isNotBlank()) {
            DialogMessage(
                modifier = Modifier.padding(top = 12.dp),
                message = message
            )
        }

        DialogTextButtons(
            modifier = Modifier.padding(top = 4.dp),
            negativeText = negativeText,
            positiveText = positiveText,
            onClickNo = onClickNo,
            onClickOk = onClickOk
        )
    }
}

@Composable
private fun DialogTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    MediumTitle(
        modifier = modifier.padding(top = 4.dp),
        title = title,
        fontColor = Color.Black,
    )
}

@Composable
private fun DialogMessage(
    modifier: Modifier = Modifier,
    message: String,
) {
    MediumDescription(
        modifier = modifier.padding(bottom = 8.dp),
        description = message,
        fontColor = Gray60,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun DialogTextButtons(
    modifier: Modifier = Modifier,
    negativeText: String,
    positiveText: String,
    onClickNo: () -> Unit,
    onClickOk: () -> Unit,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        if (negativeText.isNotBlank()) {
            DialogTextButton(
                modifier = Modifier.weight(1f),
                text = negativeText,
                fontColor = Gray60,
                onClick = onClickNo,
            )
        }
        DialogTextButton(
            modifier = Modifier.weight(1f),
            text = positiveText,
            fontColor = Purple80,
            onClick = onClickOk,
        )
    }
}

@Composable
private fun DialogTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    fontColor: Color,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        MediumDescription(
            description = text,
            fontColor = fontColor,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    PetudioDialog(
        dialogTitleRes = R.string.logout,
        dialogDescRes = R.string.logout_desc,
        onDismissClick = {},
        onConfirmClick = {},
    )
}