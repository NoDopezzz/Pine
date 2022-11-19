plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.bluetooth.client.service"
    )
}

dependencies {
    implementation(project(Project.BluetoothSDK.clientManager))
    implementation(project(Project.BluetoothSDK.utils))
    implementation(project(Project.Core.Utils.permissions))
    implementation(project(Project.Core.Utils.callbackFlow))
    implementation(project(Project.BluetoothSDK.clientCallback))
    implementation(project(Project.BluetoothSDK.clientExceptions))
    implementation(project(Project.BluetoothSDK.messages))

    implementation(Libraries.Bluetooth.nordic)
    implementation(Libraries.Koin.koinAndroid)
}