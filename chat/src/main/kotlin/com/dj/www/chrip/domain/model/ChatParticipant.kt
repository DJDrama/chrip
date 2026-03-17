package com.dj.www.chrip.domain.model

import com.dj.www.chrip.domain.type.UserId

data class ChatParticipant(
    val userId: UserId,
    val username: String,
    val email: String,
    val profilePictureUrl: String?
)