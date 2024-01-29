package com.composition.damoa.presentation.screens.home.state

import com.composition.damoa.data.model.User
import com.composition.damoa.presentation.common.base.BaseUiState

data class UserUiState(
    override val state: State = State.NONE,
    val user: User = User(
        email = "",
        socialType = User.SocialType.GOOGLE,
        point = 0,
    ),
) : BaseUiState()