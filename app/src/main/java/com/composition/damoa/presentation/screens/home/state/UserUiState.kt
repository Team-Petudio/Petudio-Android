package com.composition.damoa.presentation.screens.home.state

import com.composition.damoa.data.model.User
import com.composition.damoa.presentation.common.base.BaseUiState

data class UserUiState(
    override val state: State = State.NONE,
    val user: User = User(
        id = INVALID_USER_ID,
        email = "",
        socialType = User.SocialType.GOOGLE,
        ticket = 0,
    ),
) : BaseUiState() {
    companion object {
        const val INVALID_USER_ID = -1L
    }
}