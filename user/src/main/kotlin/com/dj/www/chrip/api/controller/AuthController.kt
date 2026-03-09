package com.dj.www.chrip.api.controller

import com.dj.www.chrip.api.dto.*
import com.dj.www.chrip.api.mappers.toAuthenticatedUserDto
import com.dj.www.chrip.api.mappers.toUserDto
import com.dj.www.chrip.service.auth.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody body: RegisterRequest
    ): UserDto {
        return authService.register(
            email = body.email,
            username = body.username,
            password = body.password,
        ).toUserDto()
    }

    @PostMapping("/login")
    fun login(
        @RequestBody body: LoginRequest
    ): AuthenticatedUserDto {
        return authService.login(
            email = body.email,
            password = body.password
        ).toAuthenticatedUserDto()
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody refreshRequest: RefreshRequest
    ): AuthenticatedUserDto {
        return authService
            .refresh(refreshToken = refreshRequest.refreshToken)
            .toAuthenticatedUserDto()
    }
}