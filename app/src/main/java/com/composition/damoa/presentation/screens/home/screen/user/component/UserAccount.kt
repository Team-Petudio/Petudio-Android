package com.composition.damoa.presentation.screens.home.screen.user.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.data.model.User
import com.composition.damoa.presentation.ui.theme.Gray10


@Composable
fun UserAccount(
    modifier: Modifier = Modifier,
    user: User,
) {
    when (user.socialType) {
        User.SocialType.GOOGLE -> GoogleAccount(modifier, user.email)
        User.SocialType.KAKAO -> KakaoAccount(modifier, user.email)
        User.SocialType.APPLE -> Unit
    }
}

@Composable
private fun GoogleAccount(
    modifier: Modifier = Modifier,
    email: String,
) {
    Account(
        modifier = modifier,
        iconRes = R.drawable.ic_account_google,
        email = email,
    )
}

@Composable
private fun KakaoAccount(
    modifier: Modifier = Modifier,
    email: String,
) {
    Account(
        modifier = modifier,
        iconRes = R.drawable.ic_account_kakao,
        email = email,
    )
}

@Composable
private fun Account(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    email: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Gray10)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconRes),
            tint = Color.Unspecified,
            contentDescription = null,
        )
        Text(
            text = email,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 8.dp),
            textAlign = TextAlign.Start,
        )
    }
}