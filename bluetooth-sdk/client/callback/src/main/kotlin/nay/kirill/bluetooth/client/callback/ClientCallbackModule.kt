package nay.kirill.bluetooth.client.callback

import nay.kirill.bluetooth.client.callback.event.ClientEventCallback
import nay.kirill.bluetooth.client.callback.message.ClientMessageCallback
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val clientCallbackModule = module {
    singleOf(::ClientEventCallback)
    singleOf(::ClientMessageCallback)
}