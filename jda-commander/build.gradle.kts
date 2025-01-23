plugins {
    kotlin("jvm") version "2.1.0"
    `maven-publish`
}

group = "xyz.yamida"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.2.2")
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
            artifactId = "jda-commander"
            version = "1.0.4"
            from(components["java"])
        }
    }
}


kotlin {
    jvmToolchain(21)
}
