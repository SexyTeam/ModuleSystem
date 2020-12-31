import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.10"
}

group = "club.eridani"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/smallshen/maven")
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("io.xiaoshen:eventsystem:1.0.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")

}
