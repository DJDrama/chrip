package com.dj.www.chrip.infra.database.mappers

import com.dj.www.chrip.domain.model.EmailVerificationToken
import com.dj.www.chrip.infra.database.entities.EmailVerificationTokenEntity

fun EmailVerificationTokenEntity.toEmailVerificationToken(): EmailVerificationToken {
    return EmailVerificationToken(
        id = id,
        token = token,
        user = user.toUser()
    )
}