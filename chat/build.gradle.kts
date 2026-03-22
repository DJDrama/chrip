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
    implementation(projects.common)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.validation)
    runtimeOnly(libs.postgresql)
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}