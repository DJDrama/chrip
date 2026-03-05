package com.dj.www.chrip

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

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
