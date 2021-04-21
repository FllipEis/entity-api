dependencies {
    kapt("org.spigotmc:plugin-annotations:1.2.3-SNAPSHOT")
    api("org.spigotmc:plugin-annotations:1.2.3-SNAPSHOT")
    api(project(":entity-api")) {
        isTransitive = false
    }
}

tasks {
    jar {
        from(configurations.runtimeClasspath.map { config -> config.map { if (it.isDirectory) it else zipTree(it) } })
    }
}