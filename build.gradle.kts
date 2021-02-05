plugins {
    kotlin("jvm") version "1.4.30"
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
}