package com.dj.www.chrip.api.dto

import com.dj.www.chrip.domain.type.UserId

data class ChatParticipantDto(
    val userId: UserId,
    val username: String,
    val email: String,
    val profilePictureUrl: String?,
)
