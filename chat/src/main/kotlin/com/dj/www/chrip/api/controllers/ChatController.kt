package com.dj.www.chrip.api.controllers

import com.dj.www.chrip.api.dto.ChatDto
import com.dj.www.chrip.api.dto.CreateChatRequest
import com.dj.www.chrip.api.mapper.toChatDto
import com.dj.www.chrip.api.util.requestUserId
import com.dj.www.chrip.infra.service.ChatService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}