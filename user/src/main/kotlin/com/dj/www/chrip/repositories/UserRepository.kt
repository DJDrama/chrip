package com.dj.www.chrip.repositories

import com.dj.www.chrip.domain.model.UserId
import com.dj.www.chrip.info.database.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, UserId> {

    fun findByEmail(email: String): UserEntity?

    fun findByEmailOrUsername(email: String, username: String): UserEntity?

}