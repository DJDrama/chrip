package com.dj.www.chrip.infra.security

import java.security.SecureRandom
import java.util.*

object TokenGenerator {
    fun generateSecureToken(): String {
        val bytes = ByteArray(size = 32)
        val secureRandom = SecureRandom()
        secureRandom.nextBytes(bytes)
        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(bytes)
    }
}