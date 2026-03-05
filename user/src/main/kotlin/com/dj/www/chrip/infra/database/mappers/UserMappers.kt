package com.dj.www.chrip.infra.database.mappers

import com.dj.www.chrip.domain.model.User
import com.dj.www.chrip.infra.database.entities.UserEntity

fun UserEntity.toUser(): User {
    return User(
        id = id!!,
        email = email,
        username = username,
        hasEmailVerified = hasVerifiedEmail
    )
}