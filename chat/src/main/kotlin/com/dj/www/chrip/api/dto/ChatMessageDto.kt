package com.dj.www.chrip.api.dto

import com.dj.www.chrip.domain.type.ChatId
import com.dj.www.chrip.domain.type.ChatMessageId
import com.dj.www.chrip.domain.type.UserId
import java.time.Instant

data class ChatMessageDto(
    val id: ChatMessageId,
    val chatId: ChatId,
    val content: String,
    val createdAt: Instant,
    val senderId: UserId
)
