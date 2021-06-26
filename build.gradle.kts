/*
 * Copyright (c) 2021 Philipp Eistrach
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

plugins {
    java
    kotlin("jvm") version "1.4.32"
    kotlin("kapt") version "1.4.32"
    id("maven-publish")
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
        compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
        implementation("com.google.inject:guice:5.0.1")
        implementation("com.google.inject.extensions:guice-assistedinject:5.0.1")
        testImplementation("junit", "junit", "4.12")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

publishing {
     repositories {
         maven{
             name = "GitHubPackages"
             setUrl("https://maven.pkg.github.com/FllipEis/entity-api")
             credentials {
                 username = System.getenv("GITHUB_ACTOR")
                 password = System.getenv("GITHUB_TOKEN")
             }
         }
     }

    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }
}
