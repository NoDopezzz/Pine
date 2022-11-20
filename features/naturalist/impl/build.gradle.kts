plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.pine.naturalist.impl",
            compose = true
    )
}

dependencies {
    implementation(Libraries.Navigation.cicerone)
    implementation(Libraries.Koin.koinAndroid)
    implementation(Libraries.DataStore.dataStore)
    implementation(Libraries.Compose.lottie)

    implementation(platform(Libraries.Compose.bom))
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.runtime)
    implementation(Libraries.Compose.foundation)
    implementation(Libraries.Compose.preview)
    debugImplementation(Libraries.Compose.debugPreview)

    implementation(project(Project.Features.naturalistApi))

    implementation(project(Project.Core.arch))
    implementation(project(Project.Core.Utils.callbackFlow))
    implementation(project(Project.Core.Utils.permissions))
    implementation(project(Project.Core.UI.button))
    implementation(project(Project.Core.UI.compose))
    implementation(project(Project.Core.UI.topbar))
    implementation(project(Project.Core.UI.res))
    implementation(project(Project.Core.UI.list))
    implementation(project(Project.Core.UI.error))

    implementation(project(Project.Features.mainApi))
    implementation(project(Project.Core.UI.compose))
    implementation(project(Project.Core.UI.button))
    implementation(project(Project.Core.UI.topbar))
    implementation(project(Project.Core.Utils.permissions))

    implementation(project(Project.BluetoothSDK.scannerApi))
    implementation(project(Project.BluetoothSDK.clientService))
    implementation(project(Project.BluetoothSDK.clientManager))
    implementation(project(Project.BluetoothSDK.clientCallback))
    implementation(project(Project.BluetoothSDK.clientExceptions))
    implementation(project(Project.BluetoothSDK.messages))

    implementation(Libraries.Permission.permissionDispatcher)
    kapt(Libraries.Permission.permissionDispatcherProcessor)
}