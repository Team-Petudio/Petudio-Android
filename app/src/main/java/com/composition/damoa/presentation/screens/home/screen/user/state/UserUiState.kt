package com.composition.damoa.presentation.screens.home.screen.user.state

import com.composition.damoa.data.model.User
import com.composition.damoa.presentation.common.base.BaseUiState

data class UserUiState(
    override val state: State = State.NONE,
    val user: User = User(
        id = INVALID_USER_ID,
        email = "",
        socialType = User.SocialType.GOOGLE,
        ticketCount = 0,
    ),
    val onLogin: () -> Unit,
    val onLogout: () -> Unit,
    val onSignOut: () -> Unit,
) : BaseUiState() {
    companion object {
        const val INVALID_USER_ID = -1L
    }
}