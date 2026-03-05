import gradle.kotlin.dsl.accessors._bb6c26e136a0a29d79404df9155f9066.allOpen
import gradle.kotlin.dsl.accessors._bb6c26e136a0a29d79404df9155f9066.java
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.kotlin

plugins {
    id("chrip.spring-boot-service")
    id("org.springframework.boot")
    kotlin("plugin.spring")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}