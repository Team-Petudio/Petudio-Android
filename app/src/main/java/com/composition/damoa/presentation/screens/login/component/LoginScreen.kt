package com.composition.damoa.presentation.screens.login.component

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composition.damoa.data.model.User
import com.composition.damoa.presentation.common.base.BaseUiState
import com.composition.damoa.presentation.screens.login.LoginViewModel
import com.composition.damoa.presentation.ui.theme.PetudioTheme


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onLoginClick: (User.SocialType) -> Unit,
) {
    val activity = LocalContext.current as ComponentActivity
    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()

    PetudioTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = { LoginTopBar { activity.finish() } },
        ) {
            LoginBackground()
            LoginForeground(onLoginClick = onLoginClick)

            if (loginUiState.state == BaseUiState.State.LOADING) LoginLoading()
        }
    }
}
