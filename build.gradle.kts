val group = "cn.cyanbukkit.climb"
val version = "0.2"

bukkit {
    name = rootProject.name
    description = "An example plugin for CyanBukkit"
    authors = listOf("CyanBukkit", "NostMC")
    website = "https://cyanbukkit.cn"
    main = "${group}.cyanlib.launcher.CyanPluginLauncher"
    // 在插件PIXGame-Bukkit加载前加载完
    loadBefore = listOf("ModuleGame-Bukkit")
    depend = listOf("Citizens")
    apiVersion = "1.13"
}

plugins {
    java
    kotlin("jvm") version "1.9.20"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

repositories {
    maven("https://nexus.cyanbukkit.cn/repository/maven-public/")
    maven("https://maven.elmakers.com/repository")
    maven("https://maven.citizensnpcs.co/repo")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
//    compileOnly("org.spigotmc:minecraft-server:1.20.4-R0.1-SNAPSHOT")
    compileOnly("org.apache.commons:commons-lang3:3.12.0")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.8.0")
    compileOnly(fileTree("libs") { include("*.jar") })
    // citizens
    compileOnly("net.citizensnpcs:citizens-main:2.0.33-SNAPSHOT")
}

kotlin {
    jvmToolchain(17)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    jar {
        archiveFileName.set("${rootProject.name}-${version}.jar")
    }

}
