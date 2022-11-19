package nay.kirill.bluetooth.client

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val clientManagerModule = module {
    factoryOf(::ClientManager)
}