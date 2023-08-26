import de.fayard.refreshVersions.core.versionFor
import extensions.implementationProjects
import extensions.implementations

plugins {
    id(Dependencies.libraryPlugin)
    id(Dependencies.kotlinPlugin)
    id(Dependencies.hiltPlugin)
    id(Dependencies.kspPlugin)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "${AppConfig.namespace}.ui.common"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementationProjects(
        ":navigation"
    )

    /* Hilt */
    implementation(Google.dagger.hilt.android)
    ksp(Google.dagger.hilt.compiler)

    /* Compose */
    implementations(
        platform(AndroidX.compose.bom),
        AndroidX.compose.material3,
        AndroidX.compose.ui,
        AndroidX.compose.ui.text.googleFonts,
        AndroidX.compose.ui.graphics,
        AndroidX.compose.ui.toolingPreview,
        AndroidX.hilt.navigationCompose
    )

    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(AndroidX.test.espresso.core)

}