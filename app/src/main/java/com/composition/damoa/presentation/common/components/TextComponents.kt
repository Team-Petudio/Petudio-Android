package com.composition.damoa.presentation.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.ui.theme.AlertIconColor
import com.composition.damoa.presentation.ui.theme.Gray60


@Composable
fun BigTitle(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    fontColor: Color = Color.Black,
) {
    BigTitle(
        modifier = modifier,
        title = stringResource(id = titleRes),
        fontColor = fontColor,
    )
}

@Composable
fun BigTitle(
    modifier: Modifier = Modifier,
    title: String,
    fontColor: Color = Color.Black,
) {
    Text(
        text = title,
        fontSize = 26.sp,
        fontWeight = FontWeight.Black,
        color = fontColor,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun MediumTitle(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    fontColor: Color = Color.Black,
    textAlign: TextAlign? = null,
) {
    MediumTitle(
        modifier = modifier,
        title = stringResource(id = titleRes),
        fontColor = fontColor,
        textAlign = textAlign,
    )
}

@Composable
fun MediumTitle(
    modifier: Modifier = Modifier,
    title: String,
    fontColor: Color = Color.Black,
    textAlign: TextAlign? = null,
) {
    Text(
        text = title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Black,
        color = fontColor,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign,
    )
}

@Composable
fun SmallTitle(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    fontColor: Color = Color.Black,
    isShowAlertIcon: Boolean = false,
) {
    SmallTitle(
        modifier = modifier,
        title = stringResource(id = titleRes),
        fontColor = fontColor,
        isShowAlertIcon = isShowAlertIcon,
    )
}

@Composable
fun SmallTitle(
    modifier: Modifier = Modifier,
    title: String,
    fontColor: Color = Color.Black,
    isShowAlertIcon: Boolean = false,
) {
    Row(modifier) {
        if (isShowAlertIcon) {
            Image(
                painter = painterResource(id = R.drawable.alert),
                contentDescription = null,
                colorFilter = ColorFilter.tint(AlertIconColor),
                modifier = Modifier.padding(end = 8.dp),
            )
        }
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = fontColor,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun TinyTitle(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    fontColor: Color = Color.Black,
    isShowAlertIcon: Boolean = false,
) {
    TinyTitle(
        modifier = modifier,
        title = stringResource(id = titleRes),
        fontColor = fontColor,
        isShowAlertIcon = isShowAlertIcon,
    )
}

@Composable
fun TinyTitle(
    modifier: Modifier = Modifier,
    title: String,
    fontColor: Color = Color.Black,
    isShowAlertIcon: Boolean = false,
) {
    Row(modifier) {
        if (isShowAlertIcon) {
            Image(
                painter = painterResource(id = R.drawable.alert),
                contentDescription = null,
                colorFilter = ColorFilter.tint(AlertIconColor),
                modifier = Modifier.padding(end = 8.dp),
            )
        }
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            color = fontColor,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun BigDescription(
    modifier: Modifier = Modifier,
    @StringRes descriptionRes: Int,
    fontColor: Color = Gray60,
) {
    BigDescription(
        modifier = modifier,
        description = stringResource(id = descriptionRes),
        fontColor = fontColor,
    )
}

@Composable
fun BigDescription(
    modifier: Modifier = Modifier,
    description: String,
    fontColor: Color = Gray60,
) {
    Text(
        text = description,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        color = fontColor,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun MediumDescription(
    modifier: Modifier = Modifier,
    @StringRes descriptionRes: Int,
    fontColor: Color = Gray60,
    textAlign: TextAlign? = null,
) {
    MediumDescription(
        modifier = modifier,
        description = stringResource(id = descriptionRes),
        fontColor = fontColor,
        textAlign = textAlign
    )
}

@Composable
fun MediumDescription(
    modifier: Modifier = Modifier,
    description: String,
    fontColor: Color = Gray60,
    textAlign: TextAlign? = null,
) {
    Text(
        text = description,
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        color = fontColor,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign,
    )
}

@Composable
fun SmallDescription(
    modifier: Modifier = Modifier,
    @StringRes descriptionRes: Int,
    fontColor: Color = Gray60,
) {
    SmallDescription(
        modifier = modifier,
        description = stringResource(id = descriptionRes),
        fontColor = fontColor,
    )
}

@Composable
fun SmallDescription(
    modifier: Modifier = Modifier,
    description: String,
    fontColor: Color = Gray60,
) {
    Text(
        text = description,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = fontColor,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
    )
}
