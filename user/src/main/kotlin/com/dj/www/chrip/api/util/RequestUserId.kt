package com.dj.www.chrip.api.util

import com.dj.www.chrip.domain.exception.UnauthorizedException
import com.dj.www.chrip.domain.model.UserId
import org.springframework.security.core.context.SecurityContextHolder

val requestUserId: UserId
    get() = SecurityContextHolder.getContext().authentication?.principal as? UserId
        ?: throw UnauthorizedException()