plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.core.ui.res"
    )
}

dependencies {
    implementation(Libraries.Koin.koinAndroid)
}
