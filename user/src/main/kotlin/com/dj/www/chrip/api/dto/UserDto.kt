package com.dj.www.chrip.api.dto

import com.dj.www.chrip.domain.model.UserId

data class UserDto(
    val id: UserId,
    val email: String,
    val username: String,
    val hasVerifiedEmail: Boolean
)
