package com.dj.www.chrip.domain.exception

import com.dj.www.chrip.domain.type.UserId

class ChatParticipantNotFoundException(
    private val id: UserId
) : RuntimeException(
    "The chat participant with the Id $id was not found."
)