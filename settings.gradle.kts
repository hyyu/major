@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("de.fayard.refreshVersions") version "0.51.0"
////                                # available:"0.60.0"
////                                # available:"0.60.1"
        id("com.android.library") version "8.1.1"
        id("org.jetbrains.kotlin.android") version "1.8.0"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("de.fayard.refreshVersions")
}

rootProject.name = "Major"
include(
    ":app",
    ":data",
    ":domain",
    ":navigation",
    ":ui:common",
    ":ui:authentication",
    ":ui:home"
)
