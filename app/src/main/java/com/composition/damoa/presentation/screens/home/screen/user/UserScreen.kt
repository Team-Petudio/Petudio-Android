package com.composition.damoa.presentation.screens.home.screen.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composition.damoa.presentation.common.components.LoginButton
import com.composition.damoa.presentation.screens.home.screen.user.component.LogoutButton
import com.composition.damoa.presentation.screens.home.screen.user.component.LogoutDialog
import com.composition.damoa.presentation.screens.home.screen.user.component.ProfileTitle
import com.composition.damoa.presentation.screens.home.screen.user.component.SignOutButton
import com.composition.damoa.presentation.screens.home.screen.user.component.SignOutDialog
import com.composition.damoa.presentation.screens.home.screen.user.component.UserAccount
import com.composition.damoa.presentation.screens.home.screen.user.component.userOptionItems.UserOptionList
import com.composition.damoa.presentation.screens.home.screen.user.state.UserUiState


@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    uiState: UserUiState,
) {
    var isShowLogoutDialog by rememberSaveable { mutableStateOf(false) }
    var isShowSignOutDialog by rememberSaveable { mutableStateOf(false) }
    val user = uiState.user
    val isLogin = uiState.isLogined

    Column(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ProfileTitle()
        if (isLogin) UserAccount(modifier = Modifier.padding(top = 28.dp), user = user)
        UserOptionList(modifier = Modifier.padding(top = 28.dp), myOwnTicketCount = user.ticketCount, isLogin = isLogin)
        Spacer(modifier = Modifier.weight(1F))
        if (isLogin) {
            LogoutButton(modifier = Modifier.padding(top = 28.dp)) { isShowLogoutDialog = true }
            SignOutButton(modifier = Modifier.padding(top = 12.dp)) { isShowSignOutDialog = true }
        } else {
            LoginButton(modifier = Modifier.padding(bottom = 28.dp), onClick = uiState.onLogin)
        }

        if (isShowLogoutDialog) {
            LogoutDialog(
                onDismissClick = { isShowLogoutDialog = false },
                onLogoutClick = {
                    uiState.onLogout()
                    isShowLogoutDialog = false
                },
            )
        }
        if (isShowSignOutDialog) {
            SignOutDialog(
                onDismissClick = { isShowSignOutDialog = false },
                onSignOutClick = {
                    uiState.onSignOut()
                    isShowSignOutDialog = false
                },
            )
        }
    }
}
