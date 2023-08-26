import extensions.implementationProjects

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
    namespace = "${AppConfig.namespace}.domain"
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

    implementationProjects(
        ":data"
    )

    /* Hilt */
    implementation(Google.dagger.hilt.android)
    ksp(Google.dagger.hilt.compiler)

    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(AndroidX.test.espresso.core)
}