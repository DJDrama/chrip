package com.dj.www.chrip.api.mappers

import com.dj.www.chrip.api.dto.AuthenticatedUserDto
import com.dj.www.chrip.api.dto.UserDto
import com.dj.www.chrip.domain.model.AuthenticatedUser
import com.dj.www.chrip.domain.model.User

fun AuthenticatedUser.toAuthenticatedUserDto(): AuthenticatedUserDto {
    return AuthenticatedUserDto(
        user = user.toUserDto(),
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        id = id,
        email = email,
        username = username,
        hasVerifiedEmail = hasEmailVerified
    )
}