package com.composition.damoa.presentation.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.composition.damoa.R
import com.composition.damoa.data.model.User.SocialType
import com.composition.damoa.di.other.AuthCompatQualifier
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.screens.home.HomeActivity
import com.composition.damoa.presentation.screens.login.authManager.AuthManager
import com.composition.damoa.presentation.screens.login.component.LoginScreen
import com.composition.damoa.presentation.screens.login.state.LoginUiEvent
import com.composition.damoa.presentation.screens.login.state.LoginUiEvent.LOGIN_FAILURE
import com.composition.damoa.presentation.screens.login.state.LoginUiEvent.LOGIN_SUCCESS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    @AuthCompatQualifier
    lateinit var authCompat: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                viewModel = viewModel,
                onLoginClick = ::login,
            )
        }

        viewModel.event.collectEvent()
    }

    private fun SharedFlow<LoginUiEvent>.collectEvent() {
        onUi {
            collectLatest { uiEvent ->
                when (uiEvent) {
                    LOGIN_FAILURE -> showToast(R.string.login_failure_message)
                    LOGIN_SUCCESS -> HomeActivity.startActivity(this@LoginActivity)
                }
            }
        }
    }

    private fun login(socialType: SocialType) {
        authCompat.login(
            socialType = socialType,
            onPreLogin = { viewModel.changeToLoading() },
            onSuccess = { accessToken, fcmToken -> viewModel.login(socialType, accessToken, fcmToken) },
            onFailure = {
                showToast(R.string.login_failure_message)
                viewModel.changeToNone()
            },
        )
    }

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent(activity, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            activity.startActivity(intent)
        }
    }
}
