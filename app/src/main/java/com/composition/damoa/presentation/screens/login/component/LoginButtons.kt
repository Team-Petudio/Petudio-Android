package com.composition.damoa.presentation.screens.login.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.data.model.User
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.ui.theme.Gray20


@Composable
fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    onClick: (User.SocialType) -> Unit,
) {
    LoginButton(
        modifier = modifier,
        onClick = { onClick(User.SocialType.GOOGLE) },
        textRes = R.string.login_with_google,
        iconRes = R.drawable.ic_account_google,
    )
}

@Composable
fun KakaoLoginButton(
    modifier: Modifier = Modifier,
    onClick: (User.SocialType) -> Unit = {},
) {
    LoginButton(
        modifier = modifier,
        onClick = { onClick(User.SocialType.KAKAO) },
        textRes = R.string.login_with_kakao,
        iconRes = R.drawable.ic_account_kakao,
    )
}

@Composable
private fun LoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes textRes: Int,
    @DrawableRes iconRes: Int,
) {
    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Gray20),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
        contentPadding = PaddingValues(vertical = 18.dp),
    ) {
        Row {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Color.Unspecified,
            )
            MediumDescription(
                descriptionRes = textRes,
                modifier = Modifier.padding(start = 12.dp),
                fontColor = Color.Black,
            )
        }
    }
}