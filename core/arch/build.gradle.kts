plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
        target = project,
        targetPackage = "nay.kirill.core.arch"
    )
}

dependencies {
    implementation(Libraries.Compose.runtime)
    implementation(Libraries.Lifecycle.viewModel)

    implementation(Libraries.Androidx.core)
    implementation(Libraries.Androidx.appCompat)
}