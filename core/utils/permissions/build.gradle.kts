plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
}

android {
    libraryConfig(
            target = project,
            targetPackage = "nay.kirill.core.utils.permissions"
    )
}

dependencies {

    implementation(Libraries.Androidx.annotation)
    implementation(Libraries.Permission.permissionDispatcher)
    implementation(Libraries.Androidx.core)
    implementation(Libraries.Androidx.appCompat)

    kapt(Libraries.Permission.permissionDispatcherProcessor)

}
