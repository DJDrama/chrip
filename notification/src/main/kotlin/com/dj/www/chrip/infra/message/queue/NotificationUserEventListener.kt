package com.dj.www.chrip.infra.message.queue

import com.dj.www.chrip.domain.events.user.UserEvent
import com.dj.www.chrip.infra.message_queue.MessageQueues
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NotificationUserEventListener {

    @RabbitListener(queues = [MessageQueues.NOTIFICATION_USER_EVENTS])
    @Transactional
    fun handleUserEvent(event: UserEvent) {
        when (event) {
            is UserEvent.Created -> {

                println("User created!")
            }

            is UserEvent.RequestResendVerification -> {

                println("Request Resend Verification")
            }

            is UserEvent.RequestResetPassword -> {
                println("Request Reset Password")
            }

            else -> Unit
        }
    }
}