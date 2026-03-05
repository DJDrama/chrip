package com.dj.www.chrip

import com.dj.www.chrip.info.database.entities.UserEntity
import com.dj.www.chrip.repositories.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class ChripApplication

fun main(args: Array<String>) {
    runApplication<ChripApplication>(*args)
}
/*

@Component
class Demo(
    private val repository: UserRepository,
) {

    @PostConstruct // run after the backend initialization is done
    fun init() {
        repository.save(
            UserEntity(
                email="test@test.com",
                username = "test",
                hashedPassword = "test123",
            )
        )
    }
}*/
