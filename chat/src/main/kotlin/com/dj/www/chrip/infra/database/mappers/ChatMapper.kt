package com.dj.www.chrip.infra.database.mappers

import com.dj.www.chrip.domain.model.Chat
import com.dj.www.chrip.domain.model.ChatMessage
import com.dj.www.chrip.domain.model.ChatParticipant
import com.dj.www.chrip.infra.database.entity.ChatEntity
import com.dj.www.chrip.infra.database.entity.ChatMessageEntity
import com.dj.www.chrip.infra.database.entity.ChatParticipantEntity

fun ChatEntity.toChat(lastMessage: ChatMessage? = null) = Chat(
    id = id!!,
    participants = participants.map {
        it.toChatParticipant()
    }.toSet(),
    lastMessage = lastMessage,
    creator = creator.toChatParticipant(),
    lastActivityAt = lastMessage?.createdAt ?: createdAt,
    createdAt = createdAt,
)

fun ChatParticipantEntity.toChatParticipant(): ChatParticipant {
    return ChatParticipant(
        userId = userId,
        username = username,
        email = email,
        profilePictureUrl = profilePictureUrl
    )
}

fun ChatParticipant.toChatParticipantEntity(): ChatParticipantEntity {
    return ChatParticipantEntity(
        userId = userId,
        username = username,
        email = email,
        profilePictureUrl = profilePictureUrl
    )
}

fun ChatMessageEntity.toChatMessage(): ChatMessage {
    return ChatMessage(
        id = id!!,
        chatId = chatId,
        sender = sender.toChatParticipant(),
        content = content,
        createdAt = createdAt
    )
}