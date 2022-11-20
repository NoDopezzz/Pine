plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.pine.mainmenu.impl",
            compose = true
    )
}

dependencies {
    implementation(Libraries.Navigation.cicerone)
    implementation(Libraries.Koin.koinAndroid)

    implementation(platform(Libraries.Compose.bom))
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.runtime)
    implementation(Libraries.Compose.foundation)
    implementation(Libraries.Compose.preview)
    debugImplementation(Libraries.Compose.debugPreview)

    implementation(project(Project.Features.pineApi))
    implementation(project(Project.Features.naturalistApi))

    implementation(project(Project.Features.mainApi))
    implementation(project(Project.Core.UI.compose))
    implementation(project(Project.Core.UI.button))
    implementation(project(Project.Core.UI.topbar))
    implementation(project(Project.Core.Utils.permissions))

    implementation(Libraries.Permission.permissionDispatcher)
    kapt(Libraries.Permission.permissionDispatcherProcessor)
}