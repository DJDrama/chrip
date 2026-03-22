package com.dj.www.chrip.infra.messaging

import com.dj.www.chrip.domain.events.user.UserEvent
import com.dj.www.chrip.domain.model.ChatParticipant
import com.dj.www.chrip.infra.message_queue.MessageQueues
import com.dj.www.chrip.service.ChatParticipantService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class ChatUserEventListener(
    private val chatParticipantService: ChatParticipantService,
) {
    @RabbitListener(queues = [MessageQueues.CHAT_USER_EVENTS])
    fun handleUserEvent(event: UserEvent) {
        when (event) {
            is UserEvent.Verified -> {
                chatParticipantService.createChatParticipant(
                    chatParticipant = ChatParticipant(
                        userId = event.userId,
                        username = event.username,
                        email = event.email,
                        profilePictureUrl = null
                    )
                )
            }

            else -> Unit
        }
    }
}