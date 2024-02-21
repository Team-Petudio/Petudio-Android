package com.composition.damoa.presentation.screens.giftcard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.presentation.common.components.MediumTitle
import com.composition.damoa.presentation.common.components.SmallDescription
import com.composition.damoa.presentation.common.components.TinyTitle
import com.composition.damoa.presentation.ui.theme.Purple60
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun GiftCardForeground(
    modifier: Modifier = Modifier,
    giftCard: GiftCard,
    onGiftCardDetailClick: () -> Unit,
) {
    val expiredFormatter = DateTimeFormatter.ofPattern("~ yyyy.MM.dd HH:mm")
    val isUsable = !giftCard.isExpired and !giftCard.isUsed

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            MediumTitle(
                modifier = Modifier.padding(top = 12.dp),
                titleRes = R.string.giftcard_item_title,
                fontColor = Color.White
            )
            when {
                isUsable -> ExpiredDate(expiredFormatter, giftCard.expiredAt)
                giftCard.isExpired -> ExpiredMessage(expiredFormatter, giftCard.expiredAt)
                giftCard.isUsed -> UsedMessage()
            }
        }
        if (isUsable) GiftCardDetailButton(onClick = onGiftCardDetailClick)
    }
}

@Composable
private fun ExpiredDate(
    expiredFormatter: DateTimeFormatter,
    expiredAt: LocalDateTime,
) {
    Row(
        modifier = Modifier
            .padding(top = 6.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SmallDescription(descriptionRes = R.string.expired_date, fontColor = Purple60)
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = expiredFormatter.format(expiredAt),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Purple60,
        )
    }
}

@Composable
private fun ExpiredMessage(
    expiredFormatter: DateTimeFormatter,
    expiredAt: LocalDateTime,
) {
    Row(
        modifier = Modifier
            .padding(top = 6.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SmallDescription(descriptionRes = R.string.expired_message, fontColor = Purple60)
        Spacer(modifier = Modifier.size(8.dp))
        SmallDescription(description = expiredFormatter.format(expiredAt), fontColor = Purple60)
    }
}

@Composable
private fun UsedMessage() {
    Row(
        modifier = Modifier
            .padding(top = 6.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TinyTitle(titleRes = R.string.used_message, fontColor = Purple60)
    }
}

@Composable
private fun GiftCardDetailButton(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Purple60,
        )
    }
}