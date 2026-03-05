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
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}