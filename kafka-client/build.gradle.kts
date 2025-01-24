plugins {
    kotlin("jvm") version "2.1.0"
    `maven-publish`
}

group = "xyz.yamida"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.kafka:kafka-clients:3.7.1")
}

publishing {
    repositories {
        maven {
            name = "yamidaRepo"
            url = uri("https://repo.yamida.xyz/releases")
            credentials {
                username = System.getenv("YAMIDA_USERNAME") ?: error("YAMIDA_USERNAME environment variable is not set")
                password = System.getenv("YAMIDA_TOKEN") ?: error("YAMIDA_TOKEN environment variable is not set")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "xyz.yamida"
            artifactId = "kafka-client"
            version = "1.0.0"
            from(components["java"])
        }
    }
}


kotlin {
    jvmToolchain(21)
}