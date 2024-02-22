package com.composition.damoa.presentation.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.Gray40


@Composable
fun PolicyButtonList(
    modifier: Modifier = Modifier,
    onTermOfUseClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        TermOfUse(onClick = onTermOfUseClick)
        Privacy(onClick = onPrivacyClick)
    }
}

@Composable
private fun TermOfUse(onClick: () -> Unit) {
    PolicyTextButton(
        textRes = R.string.terms_of_use,
        onClick = onClick,
    )
}

@Composable
private fun Privacy(onClick: () -> Unit) {
    PolicyTextButton(
        textRes = R.string.privacy_policy,
        onClick = onClick,
    )
}

@Composable
private fun PolicyTextButton(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text(
            text = stringResource(textRes),
            fontSize = 12.sp,
            color = Gray40,
        )
    }
}
