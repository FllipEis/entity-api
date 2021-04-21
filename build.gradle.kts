plugins {
    java
    kotlin("jvm") version "1.4.32"
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
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("com.google.inject:guice:5.0.1")
        testImplementation("junit", "junit", "4.12")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
