import extensions.implementations

plugins {
    id(Dependencies.libraryPlugin)
    id(Dependencies.kotlinPlugin)
    id(Dependencies.hiltPlugin)
    id(Dependencies.kotlinSerializationPlugin)
    id(Dependencies.kspPlugin)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "${AppConfig.namespace}.data"
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
}

dependencies {
    /* Hilt */
    implementation(Google.dagger.hilt.android)
    ksp(Google.dagger.hilt.compiler)

    /* Ktor */
    implementations(
        Ktor.client.android,
        Ktor.client.logging,
        Ktor.client.auth,
        Ktor.plugins.serialization.kotlinx.json,
        Ktor.client.contentNegotiation,
        KotlinX.serialization.json
    )

    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(AndroidX.test.espresso.core)
}