package nay.kirill.bluetooth.server.callback.event

import nay.kirill.core.utils.callbackFlow.CallbackFlow

/**
 * Callback for events from BLE-server service.
 */
class ServerEventCallback : CallbackFlow<ServerEvent>()