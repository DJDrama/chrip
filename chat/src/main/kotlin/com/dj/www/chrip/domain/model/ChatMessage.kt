package com.dj.www.chrip.domain.model

import com.dj.www.chrip.domain.type.ChatId
import com.dj.www.chrip.domain.type.ChatMessageId
import java.time.Instant

data class ChatMessage(
    val id: ChatMessageId, // UUID
    val chatId: ChatId,
    val sender: ChatParticipant,
    val content: String,
    val createdAt: Instant
)