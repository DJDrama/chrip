plugins {
    id("java-library")
    id("chrip.kotlin-common")
}

group = "com.dj.www"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    api(libs.kotlin.reflect)
    api(libs.jackson.module.kotlin)

    implementation(libs.spring.boot.starter.amqp)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}