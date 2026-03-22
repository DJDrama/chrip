package com.dj.www.chrip.service

import com.dj.www.chrip.domain.model.ChatParticipant
import com.dj.www.chrip.domain.type.UserId
import com.dj.www.chrip.infra.database.mappers.toChatParticipant
import com.dj.www.chrip.infra.database.mappers.toChatParticipantEntity
import com.dj.www.chrip.infra.database.repository.ChatParticipantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChatParticipantService(
    private val chatParticipantRepository: ChatParticipantRepository
) {

    fun createChatParticipant(chatParticipant: ChatParticipant) {
        chatParticipantRepository.save(chatParticipant.toChatParticipantEntity())
    }

    fun findChatParticipantById(userId: UserId): ChatParticipant? {
        return chatParticipantRepository.findByIdOrNull(userId)?.toChatParticipant()
    }

    fun findChatParticipantByEmailOrUsername(query: String): ChatParticipant? {
        val normalizedQuery = query.lowercase().trim()
        return chatParticipantRepository.findByEmailOrUsername(query = normalizedQuery)?.toChatParticipant()
    }
}