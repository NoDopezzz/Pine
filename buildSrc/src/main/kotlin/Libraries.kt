object Libraries {

    object Androidx {

        const val core = "androidx.core:core-ktx:1.7.0"

        const val appCompat = "androidx.appcompat:appcompat:1.5.1"

        const val constraint = "androidx.constraintlayout:constraintlayout:2.1.4"

        const val annotation = "androidx.annotation:annotation:1.5.0"

    }

    object Support {

        const val constraint = "com.android.support.constraint:constraint-layout:1.1.0" // Use it for legacy

    }

    object Google {

        const val material = "com.google.android.material:material:1.6.1"

        const val location = "com.google.android.gms:play-services-location:21.0.1"

    }

    object Huawei {

        const val location = "com.huawei.hms:location:6.8.0.300"

    }

    object Koin {

        private const val version = "3.3.0"

        const val koinAndroid = "io.insert-koin:koin-android:$version"

        const val compose = "io.insert-koin:koin-androidx-compose:$version"

    }

    object Navigation {

        const val cicerone = "com.github.terrakok:cicerone:7.1"

    }

    object Lifecycle {

        private const val version = "2.2.0"

        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"

    }

    object Compose {

        private const val version = "1.0.5"

        const val material = "androidx.compose.material:material"

        const val preview = "androidx.compose.ui:ui-tooling-preview"

        const val debugPreview = "androidx.compose.ui:ui-tooling"

        const val runtime = "androidx.compose.runtime:runtime:$version"

        const val foundation = "androidx.compose.foundation:foundation"

        const val bom = "androidx.compose:compose-bom:2022.10.00"

        const val lottie = "com.airbnb.android:lottie-compose:5.2.0"

    }

    object Bluetooth {

        private const val version = "2.5.1"

        const val nordic = "no.nordicsemi.android:ble-ktx:$version"

        const val nordicCommon = "no.nordicsemi.android:ble-common:$version"

        const val nordicExtensions = "no.nordicsemi.android:ble-ktx:$version"

        const val scanner = "no.nordicsemi.android.support.v18:scanner:1.6.0"

    }

    object Permission {

        private const val version = "4.8.0"

        const val permissionDispatcher = "com.github.permissions-dispatcher:permissionsdispatcher:$version"

        const val permissionDispatcherProcessor = "com.github.permissions-dispatcher:permissionsdispatcher-processor:$version"

        const val permissionDispatcherKtx = "com.github.permissions-dispatcher:ktx:$version"

    }

    object Utils {

        const val jsonSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"

    }

    object UI {

        const val radar = "com.github.TristateAndroidTeam:radarview:1.0"

        const val picasso = "com.squareup.picasso:picasso:2.5.2"

        const val zxing = "com.google.zxing:core:3.4.0"

        const val zxingEmbedded = "com.journeyapps:zxing-android-embedded:4.2.0"

    }

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"

}
