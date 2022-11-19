plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.bluetooth.server.impl"
    )
}

dependencies {
    implementation(Libraries.Bluetooth.nordic)
    implementation(Libraries.Koin.koinAndroid)

    implementation(project(Project.BluetoothSDK.utils))
    implementation(project(Project.BluetoothSDK.serverExceptions))
}