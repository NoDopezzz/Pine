package nay.kirill.bluetooth.server.impl

import android.bluetooth.BluetoothDevice
import nay.kirill.bluetooth.server.exceptions.ServerException

interface ServerConsumerCallback {

    fun onNewMessage(device: BluetoothDevice, message: ByteArray, deviceCount: Int)

    fun onNewDeviceConnected(device: BluetoothDevice, deviceCount: Int)

    fun onDeviceDisconnected(device: BluetoothDevice, deviceCount: Int)

    fun onServerReady()

    fun onFailure(throwable: ServerException)

}