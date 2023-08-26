// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Dependencies.gradlePlugin) version Versions.gradlePlugin apply false
    id(Dependencies.kotlinPlugin) version Versions.kotlinPlugin apply false
    id(Dependencies.hiltPlugin) version Versions.hiltPlugin apply false
    id(Dependencies.kspPlugin) version Versions.kspPlugin apply false
}
