package com.dj.www.chrip.api.controller

import com.dj.www.chrip.api.dto.*
import com.dj.www.chrip.api.mappers.toAuthenticatedUserDto
import com.dj.www.chrip.api.mappers.toUserDto
import com.dj.www.chrip.infra.rate_limiting.EmailRateLimiter
import com.dj.www.chrip.service.EmailVerificationService
import com.dj.www.chrip.service.PasswordResetService
import com.dj.www.chrip.service.auth.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val emailVerificationService: EmailVerificationService,
    private val passwordResetService: PasswordResetService,
    private val emailRateLimiter: EmailRateLimiter
) {

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

    @PostMapping("/logout")
    fun logout(
        @RequestBody refreshRequest: RefreshRequest
    ) {
        authService.logout(refreshToken = refreshRequest.refreshToken)
    }

    @GetMapping("/verify")
    fun verifyEmail(
        @RequestParam token: String,
    ) {
        emailVerificationService.verifyEmail(token = token)
    }

    @PostMapping("/reset-password")
    fun resetPassword(
        @Valid @RequestBody body: ResetPasswordRequest
    ) {
        passwordResetService.resetPassword(token = body.token, newPassword = body.newPassword)
    }

    @PostMapping("/forgot-password")
    fun forgotPassword(
        @Valid @RequestBody body: EmailRequest
    ) {
        passwordResetService.requestPasswordReset(email = body.email)
    }

    @PostMapping("/change-password")
    fun changePassword(
        @Valid @RequestBody body: ChangePasswordRequest
    ) {
        // TODO: Extract request user id and call service
        /*passwordResetService.changePassword(
            userId = ,
            oldPassword = body.oldPassword,
            newPassword = body.newPassword,
        )*/
    }

    @PostMapping("/resend-verification")
    fun resendVerification(
        @Valid @RequestBody body: EmailRequest
    ) {
        emailRateLimiter.withRateLimit(email = body.email) {
            emailVerificationService.resendVerificationEmail(email = body.email)
        }
    }
}