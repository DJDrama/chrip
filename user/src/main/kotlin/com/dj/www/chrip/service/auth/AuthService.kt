package com.dj.www.chrip.service.auth

import com.dj.www.chrip.domain.exception.UserAlreadyExistsException
import com.dj.www.chrip.domain.model.User
import com.dj.www.chrip.infra.database.entities.UserEntity
import com.dj.www.chrip.infra.database.mappers.toUser
import com.dj.www.chrip.infra.security.PasswordEncoder
import com.dj.www.chrip.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
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
}