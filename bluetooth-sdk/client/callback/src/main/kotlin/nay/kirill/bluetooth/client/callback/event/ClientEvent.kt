package nay.kirill.bluetooth.client.callback.event

import android.bluetooth.BluetoothDevice
import nay.kirill.bluetooth.client.exceptions.ClientException
import nay.kirill.bluetooth.messages.Message

/**
 * Events that BLE-client service produce.
 */
sealed interface ClientEvent {

    /**
     * [ConnectionSuccess] is emitted when connection to server established successfully.
     * @param device is BLE-server device we connected to.
     */
    data class ConnectionSuccess(val device: BluetoothDevice) : ClientEvent

    /**
     * [OnNewMessage] is emitted when client receives new message from server
     */
    data class OnNewMessage(val message: Message) : ClientEvent

    /**
     * [OnFailure] is emitted when some exception is thrown
     * during processing of ClientManager
     */
    data class OnFailure(val throwable: ClientException) : ClientEvent

}