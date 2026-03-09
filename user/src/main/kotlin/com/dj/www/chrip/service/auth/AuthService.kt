package com.dj.www.chrip.service.auth

import com.dj.www.chrip.domain.exception.InvalidCredentialsException
import com.dj.www.chrip.domain.exception.UserAlreadyExistsException
import com.dj.www.chrip.domain.exception.UserNotFoundException
import com.dj.www.chrip.domain.model.AuthenticatedUser
import com.dj.www.chrip.domain.model.User
import com.dj.www.chrip.domain.model.UserId
import com.dj.www.chrip.infra.database.entities.RefreshTokenEntity
import com.dj.www.chrip.infra.database.entities.UserEntity
import com.dj.www.chrip.infra.database.mappers.toUser
import com.dj.www.chrip.infra.security.PasswordEncoder
import com.dj.www.chrip.repositories.RefreshTokenRepository
import com.dj.www.chrip.repositories.UserRepository
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.time.Instant
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun register(email: String, username: String, password: String): User {
        val user = userRepository.findByEmailOrUsername(
            email = email.trim(),
            username = username.trim()
        )
        if (user != null) {
            throw UserAlreadyExistsException()
        }
        val savedUser = userRepository.save(
            UserEntity(
                email = email.trim(),
                username = username.trim(),
                hashedPassword = passwordEncoder.encode(rawPassword = password)
            )
        ).toUser()

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

        // TODO: Check for verified Email

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