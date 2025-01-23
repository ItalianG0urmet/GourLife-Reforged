plugins {
    kotlin("jvm") version "2.1.20-Beta1"
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.gradleup.shadow") version "8.3.4"
    id("xyz.jpenilla.run-paper") version "2.3.0"
}

group = "com.gourmet"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven {
        url = uri("https://repo.extendedclip.com/releases/")
    }
}


dependencies {
    paperweight.paperDevBundle("1.21.1-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    implementation("io.github.revxrsal:lamp.common:4.0.0-beta.19")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-beta.19")
    implementation("io.github.revxrsal:lamp.brigadier:4.0.0-beta.19")
}

val targetJavaVersion = 21
kotlin {

    compilerOptions {
        javaParameters = true
    }

    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks {
    shadowJar {
        minimize()
    }
    build {
        dependsOn(shadowJar)
    }
}

bukkit {
    main = "org.gourmet.gourLife.GourLife"
    apiVersion = "1.21"
    name = getName()
    version = getVersion().toString()
    author = "Gourmet"
    softDepend = listOf("PlaceholderAPI")
}
