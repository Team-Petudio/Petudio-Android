package com.composition.damoa.presentation.common.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Gray40


@Composable
fun PaymentInformationList() {
    InformationItem(
        titleRes = R.string.goods_information_title,
        descriptionRes = listOf(
            R.string.goods_information_desc1,
            R.string.goods_information_desc2,
        ),
    )
    InformationItem(
        titleRes = R.string.refund_information_title,
        descriptionRes = listOf(
            R.string.refund_information_desc1,
            R.string.refund_information_desc2,
            R.string.refund_information_desc3,
        ),
    )
}

@Composable
private fun InformationItem(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    @StringRes descriptionRes: List<Int> = emptyList(),
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val dividerPadding = if (isExpanded) 12.dp else 0.dp

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        InformationTitle(
            titleRes = titleRes,
            onClick = { isExpanded = !isExpanded },
        )
        AnimatedVisibility(visible = isExpanded) {
            DescriptionList(descriptionRes = descriptionRes)
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = dividerPadding),
            thickness = 1.dp, color = Gray20,
        )
    }
}

@Composable
private fun InformationTitle(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(titleRes),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            tint = Color.Gray,
            contentDescription = null,
        )
    }
}

@Composable
private fun DescriptionList(
    modifier: Modifier = Modifier,
    @StringRes descriptionRes: List<Int>,
) {
    Column(modifier) {
        descriptionRes.forEach { descRes ->
            DescriptionItem(descRes = descRes)
        }
    }
}

@Composable
private fun DescriptionItem(
    modifier: Modifier = Modifier,
    @StringRes descRes: Int,
) {
    Text(
        modifier = modifier,
        text = stringResource(descRes),
        fontSize = 12.sp,
        color = Gray40,
    )
}
