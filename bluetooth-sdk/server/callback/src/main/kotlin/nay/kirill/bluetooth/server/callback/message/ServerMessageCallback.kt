package nay.kirill.bluetooth.server.callback.message

import nay.kirill.core.utils.callbackFlow.CallbackFlow

/**
 * [ServerMessageCallback] is used when we want to send messages [ServerMessage] to ServerManager.
 * This callback should be observed by Service that holds ServerManager.
 */
class ServerMessageCallback : CallbackFlow<ServerMessage>()
