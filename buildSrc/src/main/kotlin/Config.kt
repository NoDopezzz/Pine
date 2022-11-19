import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object AppConfig {

    const val compileSdk = 33

    const val targetSdk = 33

    const val minSdk = 24

    const val applicationId = "nay.kirill.pine"

    const val versionName = "1.0"

    const val versionCode = 1
}

fun BaseExtension.libraryConfig(
        target: Project,
        targetPackage: String,
        compose: Boolean = false
) {
    baseConfig(target, targetPackage, compose)
}

fun BaseExtension.applicationConfig(
        target: Project,
        targetPackage: String
) {
    baseConfig(target, targetPackage)

    defaultConfig.apply {
        applicationId = AppConfig.applicationId
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        namespace = targetPackage
    }

    buildTypes.apply {
        getByName("release").apply {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug").apply {
            isMinifyEnabled = true
            isDebuggable = true
        }
    }
}

private fun BaseExtension.baseConfig(
        target: Project,
        targetPackage: String,
        useCompose: Boolean = false
) {
    compileSdkVersion(AppConfig.compileSdk)

    defaultConfig.apply {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        namespace = targetPackage
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    target.tasks.withType(KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    buildFeatures.apply {
        compose = useCompose
    }

    if (useCompose) {
        composeOptions.apply {
            kotlinCompilerExtensionVersion = "1.3.2"
        }
    }
}