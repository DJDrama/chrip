package com.dj.www.chrip.domain.model

import com.dj.www.chrip.domain.type.ChatId
import java.time.Instant

data class Chat(
    val id: ChatId,
    val participants: Set<ChatParticipant>,
    val lastMessage: ChatMessage? = null,
    val creator: ChatParticipant,
    val lastActivityAt: Instant, // either createdAt or last timestamp of the last message
    val createdAt: Instant
)