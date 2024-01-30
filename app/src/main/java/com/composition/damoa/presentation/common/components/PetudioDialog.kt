package com.composition.damoa.presentation.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.Gray60
import com.composition.damoa.presentation.ui.theme.Purple80

@Composable
fun PetudioDialog(
    painter: Painter? = null,
    iconColor: Color = Color.Black,
    @StringRes dialogTitleRes: Int,
    @StringRes dialogDescRes: Int,
    @StringRes positiveTextRes: Int = R.string.confirm,
    @StringRes negativeTextRes: Int = R.string.cancel,
    onConfirmClick: () -> Unit = {},
    onDismissClick: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissClick,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        )
    ) {
        PetudioDialogContent(
            painter = painter,
            iconColor = iconColor,
            title = stringResource(id = dialogTitleRes),
            description = stringResource(id = dialogDescRes),
            positiveText = stringResource(id = positiveTextRes),
            negativeText = stringResource(id = negativeTextRes),
            onClickOk = onConfirmClick,
            onClickNo = onDismissClick,
        )
    }
}

@Composable
private fun PetudioDialogContent(
    painter: Painter?,
    iconColor: Color,
    title: String,
    description: String,
    positiveText: String,
    negativeText: String,
    onClickOk: () -> Unit,
    onClickNo: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(40.dp)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (painter != null) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painter,
                contentDescription = null,
                tint = iconColor
            )
        }
        MediumTitle(
            modifier = Modifier.padding(top = 4.dp),
            title = title,
            fontColor = Color.Black,
        )
        if (description.isNotBlank()) {
            Spacer(modifier = Modifier.size(12.dp))
            MediumDescription(
                description = description,
                modifier = Modifier.padding(bottom = 8.dp),
                fontColor = Gray60,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            if (negativeText.isNotBlank()) {
                MediumDescription(
                    description = negativeText,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onClickNo),
                    fontColor = Gray60,
                    textAlign = TextAlign.Center,
                )
            }
            MediumDescription(
                description = positiveText,
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onClickOk),
                fontColor = Purple80,
                textAlign = TextAlign.Center,
            )
        }
    }
}