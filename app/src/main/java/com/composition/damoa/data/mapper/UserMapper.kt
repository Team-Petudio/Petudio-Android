package com.composition.damoa.data.mapper

import com.composition.damoa.data.model.User
import com.composition.damoa.data.network.dto.response.UserResponse

fun UserResponse.toDomain(token: User.Token): User = User(
    id = id,
    email = email,
    socialType = socialType.toDomain(),
    ticketCount = ticketCount,
    token = token,
)

private fun UserResponse.SocialType.toDomain(): User.SocialType = when (this) {
    UserResponse.SocialType.GOOGLE -> User.SocialType.GOOGLE
    UserResponse.SocialType.KAKAO -> User.SocialType.KAKAO
    UserResponse.SocialType.APPLE -> User.SocialType.APPLE
}
