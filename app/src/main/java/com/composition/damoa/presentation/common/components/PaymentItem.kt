package com.composition.damoa.presentation.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.Purple60

@Composable
fun PaymentItem(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int,
    isDiscounted: Boolean = false,
    originalPrice: Int,
    discountedPrice: Int = 0,
    onClick: () -> Unit = {},
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, Purple60),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
    ) {
        Column {
            PaymentTitle(titleRes = titleRes)
            PaymentDescription(descriptionRes = descriptionRes)
            PaymentPrice(isDiscounted = isDiscounted, originalPrice = originalPrice, discountedPrice = discountedPrice)
        }
    }
}

@Composable
private fun PaymentTitle(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = stringResource(titleRes),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        Icon(
            modifier = Modifier.size(26.dp),
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
        )
    }
}

@Composable
private fun PaymentDescription(
    modifier: Modifier = Modifier,
    @StringRes descriptionRes: Int,
) {
    Text(
        text = stringResource(descriptionRes),
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray,
        modifier = modifier.padding(top = 4.dp),
    )
}

@Composable
private fun PaymentPrice(
    modifier: Modifier = Modifier,
    isDiscounted: Boolean = false,
    originalPrice: Int,
    discountedPrice: Int,
) {
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        if (isDiscounted) PaymentPrice(price = originalPrice, isDiscounted = true)
        Spacer(modifier = Modifier.size(12.dp))
        PaymentPrice(price = discountedPrice)
    }
}

@Composable
private fun PaymentPrice(
    modifier: Modifier = Modifier,
    price: Int,
    isDiscounted: Boolean = false,
) {
    val priceTextSize = if (isDiscounted) 16.sp else 22.sp
    val priceFontWeight = if (isDiscounted) FontWeight.Bold else FontWeight.ExtraBold
    val priceColor = if (isDiscounted) Color.Gray else Color.Black
    val priceStrike = if (isDiscounted) {
        TextStyle(textDecoration = TextDecoration.LineThrough)
    } else {
        LocalTextStyle.current
    }
    val priceAndIconPadding = if (isDiscounted) 4.dp else 8.dp

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_ticket),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(end = priceAndIconPadding)
                .size(32.dp),
        )
        Text(
            text = "%,d".format(price),
            fontSize = priceTextSize,
            fontWeight = priceFontWeight,
            color = priceColor,
            style = priceStrike,
        )
    }
}
