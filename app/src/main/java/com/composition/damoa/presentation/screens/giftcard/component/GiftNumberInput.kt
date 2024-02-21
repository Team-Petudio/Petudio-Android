package com.composition.damoa.presentation.screens.giftcard.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.GradientButton
import com.composition.damoa.presentation.ui.theme.Gray20
import com.composition.damoa.presentation.ui.theme.Gray30
import com.composition.damoa.presentation.ui.theme.Gray40
import com.composition.damoa.presentation.ui.theme.Purple60
import com.composition.damoa.presentation.ui.theme.Purples


@Composable
fun GiftNumberInput(
    modifier: Modifier = Modifier,
    couponSerial: String,
    giftCardNumberChanged: (String) -> Unit,
    onGiftCardEnteringDone: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val purples by remember { mutableStateOf(Purples.reversed()) }

        OutlinedTextField(
            value = couponSerial,
            onValueChange = { couponSerial ->
                if (couponSerial.length <= 32) giftCardNumberChanged(couponSerial)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .weight(1F)
                .heightIn(max = 52.dp),
            placeholder = { GiftNumberHint() },
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_gift),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Purple60,
                unfocusedBorderColor = Gray20,
                cursorColor = Purple60,
            ),
        )

        GradientButton(
            modifier = Modifier
                .size(90.dp, 52.dp)
                .padding(start = 12.dp),
            fontColor = Color.White,
            fontSize = 17.sp,
            enabled = couponSerial.length == 32,
            gradient = Brush.horizontalGradient(purples),
            disabledColor = Gray30,
            text = stringResource(id = R.string.confirm),
            shape = RoundedCornerShape(12.dp),
            onClick = onGiftCardEnteringDone,
        )
    }
}

@Composable
private fun GiftNumberHint() {
    Text(
        text = stringResource(R.string.gift_number_input_hint),
        color = Gray40,
    )
}