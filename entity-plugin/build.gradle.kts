plugins {
    id("com.github.johnrengelman.shadow") version ("6.1.0")
}

apply {
    plugin("com.github.johnrengelman.shadow")
}

repositories {
    maven { setUrl("https://repo.dmulloy2.net/nexus/repository/public/") }
    maven { setUrl("https://libraries.minecraft.net/") }
}

dependencies {
    kapt("org.spigotmc:plugin-annotations:1.2.3-SNAPSHOT")
    compileOnly("org.spigotmc:plugin-annotations:1.2.3-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.6.0")
    compileOnly("com.mojang:authlib:1.5.21")
    compileOnly("com.mojang:datafixerupper:1.0.20")
    compileOnly("io.netty:netty-all:4.1.24.Final")
    api(project(":entity-api")) {
        isTransitive = false
    }
}

tasks {
    jar {
        enabled = false
        dependsOn("shadowJar")
        from(configurations.runtimeClasspath.map { config -> config.map { if (it.isDirectory) it else zipTree(it) } })
    }

    shadowJar {
        relocate("com.google.inject", "de.fllip.inject")
    }
}