plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.bluetooth.client.callback"
    )
}

dependencies {
    implementation(Libraries.Koin.koinAndroid)

    implementation(project(Project.Core.Utils.callbackFlow))
    implementation(project(Project.BluetoothSDK.clientExceptions))
    implementation(project(Project.BluetoothSDK.messages))
}