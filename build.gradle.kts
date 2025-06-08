plugins {
    kotlin("jvm") version "2.1.20-Beta1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.15"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.gradleup.shadow") version "8.3.4"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "com.gourmet"
version = "1.1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven {
        url = uri("https://repo.extendedclip.com/releases/")
    }
}


dependencies {
    implementation(kotlin("stdlib"))
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("org.json:json:20231013")
    implementation("io.github.revxrsal:lamp.common:4.0.0-rc.12")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-rc.12")
    implementation("io.github.revxrsal:lamp.brigadier:4.0.0-rc.12")
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
        archiveClassifier.set("")
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
