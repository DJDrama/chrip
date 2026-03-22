package com.dj.www.chrip.api.dto

import com.dj.www.chrip.domain.type.UserId
import jakarta.validation.constraints.Size

data class CreateChatRequest(
    @field:Size(min = 1, message = "Chat must have at least 2 unique participants")
    val otherUserIds: List<UserId>
)
