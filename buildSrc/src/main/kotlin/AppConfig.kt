object AppConfig {
    const val name = "Major"
    const val namespace = "io.cloudyhug"
    const val applicationId = "io.cloudyhug.major"
    const val compileSdk = 33
    const val minSdk = 31
    const val targetSdk = 33

    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"

    fun generateVersionCode(): Int = minSdk * 10000000 + Versions.versionMajor * 10000 + Versions.versionMinor * 100 + Versions.versionPatch
    fun generateVersionName(): String = "${Versions.versionMajor}.${Versions.versionMinor}.${Versions.versionPatch}"

}
