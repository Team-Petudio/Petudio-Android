package com.composition.damoa.data.mapper

import com.composition.damoa.data.dto.response.UserResponse
import com.composition.damoa.data.model.User

fun UserResponse.toDomain(token: User.Token): User = User(
    email = email,
    socialType = socialType.toDomain(),
    ticket = ticketCount,
    token = token,
)

private fun UserResponse.SocialType.toDomain(): User.SocialType = when (this) {
    UserResponse.SocialType.GOOGLE -> User.SocialType.GOOGLE
    UserResponse.SocialType.KAKAO -> User.SocialType.KAKAO
    UserResponse.SocialType.APPLE -> User.SocialType.APPLE
}
