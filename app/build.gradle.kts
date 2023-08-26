import de.fayard.refreshVersions.core.versionFor
import extensions.implementationProjects
import extensions.implementations

plugins {
    id(Dependencies.gradlePlugin)
    id(Dependencies.kotlinPlugin)
    id(Dependencies.hiltPlugin)
    id(Dependencies.kspPlugin)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = AppConfig.applicationId
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.generateVersionCode()
        versionName = AppConfig.generateVersionName()

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.Compose.compiler)
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    /* Project modules */
    implementationProjects(
        ":navigation",
        ":ui:common",
        ":ui:authentication",
        ":ui:home"
    )

    /* Compose */
    implementations(
        platform(AndroidX.compose.bom),
        AndroidX.compose.material3,
        AndroidX.compose.ui,
        AndroidX.compose.ui.text.googleFonts,
        AndroidX.compose.ui.graphics,
        AndroidX.compose.ui.toolingPreview
    )

    /* Hilt */
    implementation(Google.dagger.hilt.android)
    ksp(Google.dagger.hilt.compiler)

    /* Misc */
    implementations(
        AndroidX.activity.compose,
        AndroidX.navigation.compose,
        AndroidX.lifecycle.runtime.ktx
    )

    debugImplementation(AndroidX.compose.ui.testManifest)
    debugImplementation(AndroidX.compose.ui.tooling)

    testImplementation(Testing.junit4)

    androidTestImplementation(platform(AndroidX.compose.bom))
    androidTestImplementation(AndroidX.compose.ui.testJunit4)

    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(AndroidX.test.espresso.core)
}
