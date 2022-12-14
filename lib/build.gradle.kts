import java.net.URI

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.10"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    `maven-publish`
    kotlin("plugin.serialization") version "1.7.10"
}

repositories {
    // use the local Maven repository on my laptop
    mavenLocal()
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}


dependencies {
    // Use the Kotlin JUnit 5 integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // Use the JUnit 5 integration.
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api("org.apache.commons:commons-math3:3.6.1")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

val groupID = "com.codapt"
version = "0.1.0"

tasks.jar {
    manifest {
        attributes(mapOf("Implementation-Title" to project.name,
            "Implementation-Version" to project.version))
    }
}

// to only include .java files in JAR
java {
    withSourcesJar()
}

tasks.register("buildLocalPublication") {

    tasks.named("build")
    println("Built App")
    tasks.named("publishToMavenLocal")
    println("Published to Maven Local")

    doLast {
        println("Done ✅ : built a gradle project and published to Maven Local ")
    }
}
