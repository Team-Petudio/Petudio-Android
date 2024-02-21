package com.composition.damoa.presentation.screens.giftcard.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.data.model.GiftCard
import com.composition.damoa.presentation.common.components.SmallDescription
import com.composition.damoa.presentation.common.extensions.insertCharBetween
import com.composition.damoa.presentation.ui.theme.Purple20
import com.composition.damoa.presentation.ui.theme.Purple60
import com.composition.damoa.presentation.ui.theme.Purple80
import java.time.format.DateTimeFormatter


@Composable
fun GiftCardDetailForeground(
    modifier: Modifier = Modifier,
    giftCard: GiftCard,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.7F)
            .fillMaxHeight(0.75F),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_gift_box),
            contentDescription = null
        )
        SmallDescription(descriptionRes = R.string.app_name)
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = stringResource(R.string.giftcard_item_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth(0.5F)
                .padding(top = 4.dp),
            thickness = (4).dp,
            color = Purple80,
        )

        val expiredFormatter = DateTimeFormatter.ofPattern("~ yyyy.MM.dd HH:mm")
        Text(
            text = expiredFormatter.format(giftCard.expiredAt),
            modifier = Modifier.padding(top = 8.dp),
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
        )
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = stringResource(R.string.gift_card_number_title),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
        )

        val formattedGiftCode = giftCard.giftCode
            .chunked(giftCard.giftCode.length / 2)
            .joinToString("\n") { halfGiftCode -> halfGiftCode.insertCharBetween() }

        Card(
            modifier = Modifier.padding(top = 8.dp),
            shape = CutCornerShape(8.dp),
            border = BorderStroke(3.dp, Purple20),
            colors = CardDefaults.elevatedCardColors(containerColor = Purple60),
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                text = formattedGiftCode,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
            )
        }
    }
}