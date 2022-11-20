plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
}

android {
    applicationConfig(
            target = project,
            targetPackage = "nay.kirill.pine"
    )
}

dependencies {
    implementation(Libraries.Androidx.core)
    implementation(Libraries.Androidx.appCompat)
    implementation(Libraries.Google.material)
    implementation(Libraries.Androidx.constraint)
    implementation(Libraries.Koin.koinAndroid)
    implementation(Libraries.Navigation.cicerone)
    implementation(Libraries.DataStore.dataStore)

    implementation(project(Project.Core.UI.res))

    implementation(project(Project.Features.mainApi))
    implementation(project(Project.Features.mainImpl))
    implementation(project(Project.Features.pineApi))
    implementation(project(Project.Features.pineImpl))
    implementation(project(Project.Features.naturalistImpl))

    implementation(project(Project.BluetoothSDK.scannerImpl))
    implementation(project(Project.BluetoothSDK.serverManager))
    implementation(project(Project.BluetoothSDK.serverService))
    implementation(project(Project.BluetoothSDK.serverCallback))
    implementation(project(Project.BluetoothSDK.utils))
    implementation(project(Project.BluetoothSDK.clientManager))
}
