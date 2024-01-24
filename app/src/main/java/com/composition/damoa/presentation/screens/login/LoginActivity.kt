package com.composition.damoa.presentation.screens.login

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.data.model.Account.SocialType
import com.composition.damoa.di.other.GoogleAuth
import com.composition.damoa.di.other.KakaoAuth
import com.composition.damoa.presentation.common.base.BaseUiState.State
import com.composition.damoa.presentation.common.components.GradientPetudioSubTitle
import com.composition.damoa.presentation.common.components.GradientPetudioTitle
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.common.extensions.repeatOnStarted
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.screens.login.authManager.AuthManager
import com.composition.damoa.presentation.screens.login.state.LoginUiEvent
import com.composition.damoa.presentation.ui.theme.Gray20
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    @GoogleAuth
    @Inject
    lateinit var googleAuthManager: AuthManager

    @KakaoAuth
    @Inject
    lateinit var kakaoAuthManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(onLogin = ::login)
        }
        collect()
    }

    private fun collect() {
        collectLoginUiState()
        collectLoginUiEvent()
    }

    private fun collectLoginUiState() {
        repeatOnStarted {
            viewModel.loginUiState.collectLatest { uiState ->
                when (uiState.state) {
                    State.NONE -> Unit
                    State.LOADING -> Unit
                    State.SUCCESS -> Unit
                }
            }
        }
    }

    private fun collectLoginUiEvent() {
        repeatOnStarted {
            viewModel.loginUiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    LoginUiEvent.NONE -> Unit
                    LoginUiEvent.LOGIN_FAILURE -> onUi { showToast(R.string.login_failure_message) }
                    LoginUiEvent.LOGIN_SUCCESS -> finish()
                }
            }
        }
    }

    private fun login(socialType: SocialType) {
        when (socialType) {
            SocialType.GOOGLE -> googleAuthManager.login(
                onSuccess = { accessToken, fcmToken -> viewModel.login(SocialType.GOOGLE, accessToken, fcmToken) },
                onFailure = { showToast(R.string.login_failure_message) }
            )

            SocialType.KAKAO -> kakaoAuthManager.login(
                onSuccess = { accessToken, fcmToken -> viewModel.login(SocialType.KAKAO, accessToken, fcmToken) },
                onFailure = { showToast(R.string.login_failure_message) }
            )

            SocialType.APPLE -> Unit
        }
    }
}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
private fun LoginScreen(
    modifier: Modifier = Modifier,
    onLogin: (SocialType) -> Unit = {},
) {
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { LoginTopBar { (context as? Activity)?.finish() } },
    ) {
        LoginBackground()
        LoginForeground(onLogin = onLogin)
    }
}

@Composable
private fun LoginForeground(
    onLogin: (SocialType) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.25F))
        LoginContent(onLogin = onLogin)
    }
}

@Composable
private fun LoginBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        val whiteGradientBrush =
            Brush.verticalGradient(
                colors = listOf(Color(0x80FFFFFF), Color.White, Color.White),
            )

        Image(
            painter = painterResource(id = R.drawable.img_photo_upload_good_example1),
            contentDescription = null,
            modifier =
            Modifier
                .fillMaxWidth()
                .aspectRatio(1F),
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .background(whiteGradientBrush),
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LoginTopBar(onNavigationClick: () -> Unit = {}) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.navigate_back),
                    tint = Color.Black,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
    )
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    onLogin: (SocialType) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(0.7F),
        )

        val offsetModifier = Modifier.offset(y = (-40).dp)
        GradientPetudioTitle(modifier = offsetModifier, fontSize = 60.sp)
        GradientPetudioSubTitle(modifier = offsetModifier, fontSize = 22.sp)
        GoogleLoginButton(
            modifier = offsetModifier
                .padding(top = 40.dp)
                .padding(horizontal = 20.dp),
            onClick = onLogin,
        )
        KakaoLoginButton(
            modifier = offsetModifier
                .padding(top = 12.dp)
                .padding(horizontal = 20.dp),
            onClick = onLogin,
        )
    }
}

@Composable
private fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    onClick: (SocialType) -> Unit = {},
) {
    LoginButton(
        modifier = modifier,
        onClick = { onClick(SocialType.GOOGLE) },
        textRes = R.string.login_with_google,
        iconRes = R.drawable.ic_account_google,
    )
}

@Composable
private fun KakaoLoginButton(
    modifier: Modifier = Modifier,
    onClick: (SocialType) -> Unit = {},
) {
    LoginButton(
        modifier = modifier,
        onClick = { onClick(SocialType.KAKAO) },
        textRes = R.string.login_with_kakao,
        iconRes = R.drawable.ic_account_kakao,
    )
}

@Composable
private fun LoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
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

@Preview
@Composable
private fun LoginPreview() {
    LoginScreen()
}
