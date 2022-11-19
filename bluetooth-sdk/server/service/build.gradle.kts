plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.bluetooth.server.service"
    )
}

dependencies {
    implementation(project(Project.BluetoothSDK.serverManager))
    implementation(project(Project.BluetoothSDK.utils))
    implementation(project(Project.Core.Utils.permissions))
    implementation(project(Project.BluetoothSDK.serverCallback))
    implementation(project(Project.Core.Utils.callbackFlow))
    implementation(project(Project.BluetoothSDK.serverExceptions))
    implementation(project(Project.BluetoothSDK.messages))

    implementation(Libraries.Bluetooth.nordic)
    implementation(Libraries.Koin.koinAndroid)
}