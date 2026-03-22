package com.dj.www.chrip.api.controllers

import com.dj.www.chrip.api.dto.AddParticipantToChatDto
import com.dj.www.chrip.api.dto.ChatDto
import com.dj.www.chrip.api.dto.CreateChatRequest
import com.dj.www.chrip.api.mapper.toChatDto
import com.dj.www.chrip.api.util.requestUserId
import com.dj.www.chrip.domain.type.ChatId
import com.dj.www.chrip.service.ChatService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chat")
class ChatController(private val chatService: ChatService) {

    @PostMapping
    fun createChat(
        @Valid @RequestBody request: CreateChatRequest
    ): ChatDto {
        return chatService.createChat(
            creatorId = requestUserId,
            otherUserIds = request.otherUserIds.toSet()
        ).toChatDto()
    }

    @PostMapping("/{chatId}/add")
    fun addChatParticipants(
        @PathVariable chatId: ChatId,
        @Valid @RequestBody request: AddParticipantToChatDto
    ): ChatDto {
        return chatService.addParticipantsToChat(
            requestUserId = requestUserId,
            chatId = chatId,
            userIds = request.userIds.toSet(),
        ).toChatDto()
    }

    @DeleteMapping("/{chatId}/leave")
    fun leaveChat(@PathVariable chatId: ChatId) {
        chatService.removeParticipantFromChat(
            chatId = chatId,
            userId = requestUserId,
        )
    }
}