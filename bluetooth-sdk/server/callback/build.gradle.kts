plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.bluetooth.server.callback"
    )
}

dependencies {
    implementation(Libraries.coroutines)
    implementation(Libraries.Koin.koinAndroid)

    implementation(project(Project.Core.Utils.callbackFlow))
    implementation(project(Project.BluetoothSDK.serverExceptions))
    implementation(project(Project.BluetoothSDK.messages))
}