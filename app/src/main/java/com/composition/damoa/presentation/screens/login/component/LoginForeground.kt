package com.composition.damoa.presentation.screens.login.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.data.model.User
import com.composition.damoa.presentation.common.components.GradientPetudioSubTitle
import com.composition.damoa.presentation.common.components.GradientPetudioTitle


@Composable
fun LoginForeground(
    onLoginClick: (User.SocialType) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.25F))
        LoginContent(onLoginClick = onLoginClick)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    onLoginClick: (User.SocialType) -> Unit,
) {
    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val offsetModifier = Modifier.offset(y = (-40).dp)

            AppLogo()
            GradientPetudioTitle(modifier = offsetModifier, fontSize = 60.sp)
            GradientPetudioSubTitle(modifier = offsetModifier, fontSize = 22.sp)
            GoogleLoginButton(
                modifier = offsetModifier
                    .padding(top = 40.dp)
                    .padding(horizontal = 20.dp),
                onClick = onLoginClick,
            )
            KakaoLoginButton(
                modifier = offsetModifier
                    .padding(top = 12.dp)
                    .padding(horizontal = 20.dp),
                onClick = onLoginClick,
            )
        }
    }
}

@Composable
private fun AppLogo() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth(0.7F),
    )
}
