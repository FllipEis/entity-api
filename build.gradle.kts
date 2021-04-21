plugins {
    java
    kotlin("jvm") version "1.4.32"
    kotlin("kapt") version "1.4.32"
}

allprojects {

    group = "de.fllip"
    version = "2.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

}

subprojects {
    apply {
        plugin("java")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.kapt")
    }

    repositories {
        maven { setUrl("https://hub.spigotmc.org/nexus/content/repositories/snapshots") }
    }

    dependencies {
        implementation(kotlin("stdlib"))
        api("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
        implementation("com.google.inject:guice:5.0.1")
        implementation("com.google.inject.extensions:guice-assistedinject:5.0.1")
        testImplementation("junit", "junit", "4.12")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
