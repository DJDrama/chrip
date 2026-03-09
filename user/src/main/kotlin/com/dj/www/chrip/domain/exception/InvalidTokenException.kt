package com.dj.www.chrip.domain.exception

class InvalidTokenException(
    override val message: String?
) : RuntimeException(
    message ?: "Invalid Token"
)