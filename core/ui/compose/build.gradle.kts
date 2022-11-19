plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
        targetPackage = "nay.kirill.core.compose",
        target = project,
        compose = true
    )
}

dependencies {
    implementation(platform(Libraries.Compose.bom))
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.runtime)
}