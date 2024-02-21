package com.composition.damoa.presentation.screens.giftcard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.composition.damoa.R


@Composable
fun GiftCardButtons(
    modifier: Modifier = Modifier,
    onCopyClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextButton(onClick = onCopyClick) {
            Text(
                text = stringResource(R.string.copy),
                color = Color.Black,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        TextButton(onClick = onSaveClick) {
            Text(
                text = stringResource(R.string.save),
                color = Color.Black,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}