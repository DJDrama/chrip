package com.dj.www.chrip.api.controllers

import com.dj.www.chrip.api.dto.ChatParticipantDto
import com.dj.www.chrip.api.mapper.toChatParticipantDto
import com.dj.www.chrip.api.util.requestUserId
import com.dj.www.chrip.service.ChatParticipantService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/chat/participants")
class ChatParticipantController(private val chatParticipantService: ChatParticipantService) {

    @GetMapping
    fun getChatParticipantsByUsernameOrEmail(
        @RequestParam(required = false) query: String?
    ): ChatParticipantDto {
        val participant = if (query == null) {
            chatParticipantService.findChatParticipantById(requestUserId)
        } else {
            chatParticipantService.findChatParticipantByEmailOrUsername(query = query)
        }
        return participant?.toChatParticipantDto()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}