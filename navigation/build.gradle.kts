import de.fayard.refreshVersions.core.versionFor

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
    namespace = "${AppConfig.namespace}.navigation"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.Compose.compiler)
    }
}

dependencies {
    implementation(platform(AndroidX.compose.bom))
    implementation(AndroidX.navigation.compose)

    /* Hilt */
    implementation(Google.dagger.hilt.android)
    ksp(Google.dagger.hilt.compiler)

    implementation(Google.android.material)
    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(AndroidX.test.espresso.core)
}
