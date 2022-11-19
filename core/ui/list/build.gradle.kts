plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.core.ui.list",
            compose = true
    )
}

dependencies {
    implementation(platform(Libraries.Compose.bom))
    implementation(Libraries.Compose.material)
    implementation(Libraries.Compose.runtime)

    implementation(Libraries.Compose.preview)
    debugImplementation(Libraries.Compose.debugPreview)

    implementation(project(Project.Core.UI.compose))
}