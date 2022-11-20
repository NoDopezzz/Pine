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
    implementation(Libraries.DataStore.dataStore)
    implementation(Libraries.Utils.jsonSerialization)

    implementation(project(Project.BluetoothSDK.messages))
    implementation(project(Project.BluetoothSDK.utils))
    implementation(project(Project.BluetoothSDK.serverExceptions))
}