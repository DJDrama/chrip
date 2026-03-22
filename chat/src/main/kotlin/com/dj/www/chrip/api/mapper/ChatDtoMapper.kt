package com.dj.www.chrip.api.mapper

import com.dj.www.chrip.api.dto.ChatDto
import com.dj.www.chrip.api.dto.ChatMessageDto
import com.dj.www.chrip.api.dto.ChatParticipantDto
import com.dj.www.chrip.domain.model.Chat
import com.dj.www.chrip.domain.model.ChatMessage
import com.dj.www.chrip.domain.model.ChatParticipant

fun Chat.toChatDto(): ChatDto {
    return ChatDto(
        id = id,
        participants = participants.map { it.toChatParticipantDto() },
        lastActivityAt = lastActivityAt,
        lastMessage = lastMessage?.toChatMessageDto(),
        creator = creator.toChatParticipantDto()
    )
}

fun ChatMessage.toChatMessageDto(): ChatMessageDto {
    return ChatMessageDto(
        id = id,
        chatId = chatId,
        content = content,
        createdAt = createdAt,
        senderId = sender.userId
    )
}

fun ChatParticipant.toChatParticipantDto(): ChatParticipantDto {
    return ChatParticipantDto(
        userId = userId,
        username = username,
        email = email,
        profilePictureUrl = profilePictureUrl,
    )
}