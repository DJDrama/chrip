package com.dj.www.chrip.service

import com.dj.www.chrip.domain.exception.ChatParticipantNotFoundException
import com.dj.www.chrip.domain.exception.InvalidChatSizeException
import com.dj.www.chrip.domain.model.Chat
import com.dj.www.chrip.domain.type.UserId
import com.dj.www.chrip.infra.database.entity.ChatEntity
import com.dj.www.chrip.infra.database.mappers.toChat
import com.dj.www.chrip.infra.database.repository.ChatParticipantRepository
import com.dj.www.chrip.infra.database.repository.ChatRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val chatParticipantRepository: ChatParticipantRepository
) {

    @Transactional
    fun createChat(
        creatorId: UserId,
        otherUserIds: Set<UserId>
    ): Chat {
        val otherParticipants = chatParticipantRepository.findByUserIdIn(userIds = otherUserIds)
        val allParticipants = (otherParticipants + creatorId)
        if (allParticipants.size < 2) {
            throw InvalidChatSizeException()
        }

        val creator = chatParticipantRepository.findByIdOrNull(creatorId)
            ?: throw ChatParticipantNotFoundException(creatorId)

        return chatRepository.save(
            ChatEntity(
                creator = creator,
                participants = setOf(creator) + otherParticipants
            )
        ).toChat(lastMessage = null)
    }
}