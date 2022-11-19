package nay.kirill.bluetooth.scanner.impl

import nay.kirill.bluetooth.scanner.api.BluetoothScanner
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val bluetoothScannerModule = module {
    factoryOf(::BluetoothScannerImpl).withOptions {
        bind<BluetoothScanner>()
    }
}