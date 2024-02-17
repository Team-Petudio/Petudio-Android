package com.composition.damoa.presentation.screens.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.composition.damoa.R
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.screens.home.component.HomeScreen
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent.LOGOUT_FAILURE
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent.LOGOUT_SUCCESS
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent.NEED_LOGIN
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent.SIGN_OUT_FAILURE
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent.SIGN_OUT_SUCCESS
import com.composition.damoa.presentation.screens.home.state.HomeUiEvent.TOKEN_EXPIRED
import com.composition.damoa.presentation.screens.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeScreen(viewModel = viewModel)
        }

        viewModel.event.collectEvent()
    }

    private fun SharedFlow<HomeUiEvent>.collectEvent() {
        onUi {
            collectLatest { event ->
                when (event) {
                    LOGOUT_SUCCESS, SIGN_OUT_SUCCESS -> startActivity(this@HomeActivity)
                    LOGOUT_FAILURE, SIGN_OUT_FAILURE -> showToast(R.string.network_failure_message)
                    NEED_LOGIN, TOKEN_EXPIRED -> LoginActivity.startActivity(this@HomeActivity)
                }
            }
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val homeStartIntent = Intent(context, HomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(homeStartIntent)
        }
    }
}
