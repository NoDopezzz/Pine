package nay.kirill.bluetooth.server.callback

import nay.kirill.bluetooth.server.callback.event.ServerEventCallback
import nay.kirill.bluetooth.server.callback.message.ServerMessageCallback
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val serverCallbackModule = module {
    singleOf(::ServerEventCallback)
    singleOf(::ServerMessageCallback)
}