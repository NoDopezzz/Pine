plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.bluetooth.scanner.api"
    )
}

dependencies {
    implementation(Libraries.coroutines)
    implementation(Libraries.Bluetooth.scanner)
}