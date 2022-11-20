plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.bluetooth.utils"
    )
}

dependencies {
    implementation(Libraries.DataStore.dataStore)
}