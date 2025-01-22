plugins {
    kotlin("jvm") version "2.1.0"
}

group = "xyz.yamida"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.kafka:kafka-clients:3.5.1")
}


kotlin {
    jvmToolchain(21)
}