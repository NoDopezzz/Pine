package nay.kirill.pine

import android.app.Application
import nay.kirill.bluetooth.client.callback.clientCallbackModule
import nay.kirill.bluetooth.client.clientManagerModule
import nay.kirill.bluetooth.scanner.impl.bluetoothScannerModule
import nay.kirill.bluetooth.server.callback.serverCallbackModule
import nay.kirill.core.ui.res.resourceModule
import nay.kirill.pine.di.mainModule
import nay.kirill.pine.mainmenu.impl.api.mainMenuModule
import nay.kirill.pine.navigation.navigationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)

            modules(appModules)
        }
    }

    private val appModules by lazy {
        listOfNotNull(
                navigationModule,
                mainModule,
                bluetoothScannerModule,
                resourceModule,
                clientManagerModule,
                serverCallbackModule,
                clientCallbackModule,

                // Features
                mainMenuModule
        )
    }

}