plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.pine.mainmenu.api"
    )
}

dependencies {
    implementation(Libraries.Navigation.cicerone)
}