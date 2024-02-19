package com.composition.damoa.presentation.screens.home.screen.user.component.userOptionItems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigDescription


@Composable
fun GiftCard(onClick: () -> Unit) {
    UserOptionItem(
        item = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                BigDescription(
                    descriptionRes = R.string.item_giftcard,
                    fontColor = Color.Black,
                )
                Icon(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_giftcard),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }
        },
        onClick = onClick,
    )
}