plugins {
    id("chrip.spring-boot-app")
}

group = "com.dj.www"
version = "0.0.1-SNAPSHOT"
description = "chrip"

dependencies {
    // need enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS") in settings.gradle.kts
    implementation(projects.user)
    implementation(projects.chat)
    implementation(projects.notification)
    implementation(projects.common)

    implementation(libs.kotlin.reflect)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.amqp)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.data.redis)
    runtimeOnly(libs.postgresql)
}