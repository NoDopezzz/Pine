plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.pine.pine.impl",
            compose = true
    )
}

dependencies {
    implementation(Libraries.Androidx.appCompat)
    implementation(Libraries.Navigation.cicerone)
    implementation(Libraries.Koin.koinAndroid)

    implementation(project(Project.Features.pineApi))

    implementation(platform(Libraries.Compose.bom))
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.runtime)
    implementation(Libraries.Compose.foundation)
    implementation(Libraries.Compose.preview)
    debugImplementation(Libraries.Compose.debugPreview)

    implementation(project(Project.Core.UI.compose))
    implementation(project(Project.Core.UI.button))
    implementation(project(Project.Core.UI.topbar))
    implementation(project(Project.Core.Utils.permissions))
}