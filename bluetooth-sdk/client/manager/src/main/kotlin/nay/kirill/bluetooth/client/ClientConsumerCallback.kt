package nay.kirill.bluetooth.client

import android.bluetooth.BluetoothDevice
import nay.kirill.bluetooth.client.exceptions.ClientException

interface ClientConsumerCallback {

    fun onNewMessage(device: BluetoothDevice, message: ByteArray)

    fun onFailure(throwable: ClientException)

}