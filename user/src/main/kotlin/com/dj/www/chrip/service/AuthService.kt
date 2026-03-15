package com.dj.www.chrip.service.auth

import com.dj.www.chrip.domain.events.user.UserEvent
import com.dj.www.chrip.domain.exception.*
import com.dj.www.chrip.domain.model.AuthenticatedUser
import com.dj.www.chrip.domain.model.User
import com.dj.www.chrip.domain.type.UserId
import com.dj.www.chrip.infra.database.entities.RefreshTokenEntity
import com.dj.www.chrip.infra.database.entities.UserEntity
import com.dj.www.chrip.infra.database.mappers.toUser
import com.dj.www.chrip.infra.message_queue.EventPublisher
import com.dj.www.chrip.infra.security.PasswordEncoder
import com.dj.www.chrip.repositories.RefreshTokenRepository
import com.dj.www.chrip.repositories.UserRepository
import com.dj.www.chrip.service.EmailVerificationService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.time.Instant
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val emailVerificationService: EmailVerificationService,
    private val eventPublisher: EventPublisher
) {

    @Transactional
    fun register(email: String, username: String, password: String): User {
        val trimmedEmail = email.trim()
        val user = userRepository.findByEmailOrUsername(
            email = trimmedEmail,
            username = username.trim()
        )
        if (user != null) {
            throw UserAlreadyExistsException()
        }

        val savedUser = userRepository.saveAndFlush(
            UserEntity(
                email = trimmedEmail,
                username = username.trim(),
                hashedPassword = passwordEncoder.encode(rawPassword = password)
            )
        ).toUser()

        val token = emailVerificationService.createVerificationToken(email = trimmedEmail)
        eventPublisher.publish(
            event = UserEvent.Created(
                userId = savedUser.id,
                email = savedUser.email,
                username = savedUser.username,
                verificationToken = token.token
            )
        )

        return savedUser
    }

    fun login(
        email: String,
        password: String
    ): AuthenticatedUser {
        val user = userRepository.findByEmail(email = email.trim())
            ?: throw InvalidCredentialsException()
        if (!passwordEncoder.matches(password, user.hashedPassword)) {
            throw InvalidCredentialsException()
        }

        if (!user.hasVerifiedEmail) {
            throw EmailNotVerifiedException()
        }

        return user.id?.let { userId ->
            val accessToken = jwtService.generateAccessToken(userId = userId)
            val refreshToken = jwtService.generateRefreshToken(userId = userId)
            storeRefreshToken(userId, refreshToken)

            AuthenticatedUser(
                user = user.toUser(),
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } ?: throw UserNotFoundException()
    }

    @Transactional
    fun refresh(refreshToken: String): AuthenticatedUser {
        if (!jwtService.validateRefreshToken(token = refreshToken)) {
            throw InvalidTokenException(
                message = "Invalid refresh token",
            )
        }
        val userId = jwtService.getUserIdFromToken(token = refreshToken)
        val user = userRepository.findByIdOrNull(id = userId) ?: throw UserNotFoundException()
        val hashed = hashToken(token = refreshToken)

        return user.id?.let { userId ->
            refreshTokenRepository.findByUserIdAndHashedToken(
                userId = userId,
                hashedToken = hashed
            ) ?: throw InvalidTokenException("Invalid refresh token")

            refreshTokenRepository.deleteByUserIdAndHashedToken(
                userId = userId,
                hashedToken = hashed
            )

            val newAccessToken = jwtService.generateAccessToken(userId = userId)
            val newRefreshToken = jwtService.generateRefreshToken(userId = userId)
            storeRefreshToken(
                userId = userId,
                token = newRefreshToken
            )
            AuthenticatedUser(
                user = user.toUser(),
                accessToken = newAccessToken,
                refreshToken = newRefreshToken
            )
        } ?: throw UserNotFoundException()
    }

    @Transactional
    fun logout(refreshToken: String) {
        val userId = jwtService.getUserIdFromToken(token = refreshToken)
        val hashed = hashToken(refreshToken)
        refreshTokenRepository.deleteByUserIdAndHashedToken(userId = userId, hashedToken = hashed)
    }

    private fun storeRefreshToken(userId: UserId, token: String) {
        val hashed = hashToken(token = token)
        val expiryMs = jwtService.refreshTokenValidityMs
        val expiresAt = Instant.now().plusMillis(expiryMs)
        refreshTokenRepository.save(
            RefreshTokenEntity(
                userId = userId,
                expiresAt = expiresAt,
                hashedToken = hashed
            )
        )
    }

    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }
}