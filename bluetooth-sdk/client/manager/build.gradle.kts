plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.bluetooth.client"
    )
}

dependencies {
    implementation(Libraries.Bluetooth.nordic)

    implementation(project(Project.BluetoothSDK.utils))
    implementation(project(Project.BluetoothSDK.clientExceptions))

    implementation(Libraries.Koin.koinAndroid)

}