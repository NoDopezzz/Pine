package nay.kirill.bluetooth.server.callback.message

import nay.kirill.bluetooth.messages.Message

/**
 * [ServerMessage] that send to ServerService to communicate with ServerManager
 */
sealed interface ServerMessage {

    /**
     * [WriteCharacteristic] is used to send messages to connected devices
     * @param message is message that connected device will get
     * @param deviceAddress is id of target device. If [deviceAddress] is null
     * message will be sent to all connected devices
     */
    data class WriteCharacteristic(val message: Message, val deviceAddress: String? = null) : ServerMessage

}
