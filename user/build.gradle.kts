plugins {
    id("java-library")
    id("chrip.spring-boot-service")
    kotlin("plugin.jpa")
}

group = "com.dj.www"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}