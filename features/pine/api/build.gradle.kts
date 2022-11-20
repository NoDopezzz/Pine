plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.pine.pine.api"
    )
}

dependencies {
    implementation(Libraries.Navigation.cicerone)

    implementation(Libraries.Androidx.appCompat)
}