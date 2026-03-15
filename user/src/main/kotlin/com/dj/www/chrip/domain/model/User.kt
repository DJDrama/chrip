package com.dj.www.chrip.domain.model

import com.dj.www.chrip.domain.type.UserId


data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val hasEmailVerified: Boolean,
)
