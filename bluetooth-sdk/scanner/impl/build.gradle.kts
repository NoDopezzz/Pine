plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.bluetooth.scanner.impl"
    )
}

dependencies {
    implementation(Libraries.Androidx.core)
    implementation(Libraries.Androidx.appCompat)

    implementation(Libraries.Bluetooth.nordic)
    implementation(Libraries.Bluetooth.nordicCommon)
    implementation(Libraries.Bluetooth.nordicExtensions)

    implementation(Libraries.Bluetooth.scanner)

    implementation(Libraries.coroutines)
    implementation(Libraries.Koin.koinAndroid)

    implementation(project(Project.BluetoothSDK.scannerApi))
    implementation(project(Project.BluetoothSDK.utils))

}