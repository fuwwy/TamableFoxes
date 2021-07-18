plugins {
    java
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group "net.seanomik.tamablefoxes"
version "1.8.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/nms/")
    maven("https://jitpack.io")
}

// Workaround to support multiple mc versions
val v1_17_1_R1: Configuration by configurations.creating { extendsFrom(configurations.compileOnly.get()) }

dependencies {
    compileOnly("org.spigotmc:spigot:1.17.1-R0.1-SNAPSHOT")
    implementation("com.github.WesJD.AnvilGUI:anvilgui:5d0f592c63")
    implementation("org.ow2.asm:asm:9.1")
    implementation("org.ow2.asm:asm-tree:9.1")
}

sourceSets {
    main {
        compileClasspath += v1_17_1_R1
    }
}