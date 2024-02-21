package com.composition.damoa.presentation.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.composition.damoa.R
import com.composition.damoa.data.model.User.SocialType
import com.composition.damoa.di.other.GoogleAuth
import com.composition.damoa.di.other.KakaoAuth
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

    @GoogleAuth
    @Inject
    lateinit var googleAuthManager: AuthManager

    @KakaoAuth
    @Inject
    lateinit var kakaoAuthManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginScreen(
                viewModel = viewModel,
                onLoginClick = ::login
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
        viewModel.changeToLoading()

        when (socialType) {
            SocialType.GOOGLE -> googleAuthManager.login(
                onSuccess = { accessToken, fcmToken -> viewModel.login(SocialType.GOOGLE, accessToken, fcmToken) },
                onFailure = {
                    onUi {
                        showToast(R.string.login_failure_message)
                        viewModel.changeToNone()
                    }
                }
            )

            SocialType.KAKAO -> kakaoAuthManager.login(
                onSuccess = { accessToken, fcmToken -> viewModel.login(SocialType.KAKAO, accessToken, fcmToken) },
                onFailure = {
                    onUi {
                        showToast(R.string.login_failure_message)
                        viewModel.changeToNone()
                    }
                }
            )

            SocialType.APPLE -> onUi { showToast(R.string.login_failure_message) }
        }
    }

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent(activity, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            activity.startActivity(intent)
        }
    }
}
