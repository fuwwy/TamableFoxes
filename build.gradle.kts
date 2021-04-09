plugins {
    java
}

group "net.seanomik.tamablefoxes"
version "1.8.1-SNAPSHOT"

repositories {
    maven("https://repo.codemc.io/repository/nms/")
    maven("https://jitpack.io")
}

// Workaround to support multiple mc versions
val v1_14_R1: Configuration by configurations.creating { extendsFrom(configurations.compileOnly.get()) }
val v1_15_R1: Configuration by configurations.creating { extendsFrom(configurations.compileOnly.get()) }
val v1_16_R1: Configuration by configurations.creating { extendsFrom(configurations.compileOnly.get()) }
val v1_16_R2: Configuration by configurations.creating { extendsFrom(configurations.compileOnly.get()) }
val v1_16_R3: Configuration by configurations.creating { extendsFrom(configurations.compileOnly.get()) }

dependencies {
    v1_14_R1("org.spigotmc:spigot:1.14-R0.1-SNAPSHOT")
    v1_15_R1("org.spigotmc:spigot:1.15-R0.1-SNAPSHOT")
    v1_16_R1("org.spigotmc:spigot:1.16.1-R0.1-SNAPSHOT")
    v1_16_R2("org.spigotmc:spigot:1.16.2-R0.1-SNAPSHOT")
    v1_16_R3("org.spigotmc:spigot:1.16.4-R0.1-SNAPSHOT")
    compileOnly("com.github.WesJD.AnvilGUI:anvilgui:5d0f592c63")
}

sourceSets {
    main {
        compileClasspath += v1_14_R1 + v1_15_R1 + v1_16_R1 + v1_16_R2 + v1_16_R3
    }
}