package com.composition.damoa.presentation.screens.store.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigTitle


@Composable
fun StoreTitle(
    modifier: Modifier = Modifier,
) {
    BigTitle(
        modifier = modifier,
        titleRes = R.string.store_title
    )
}