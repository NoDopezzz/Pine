package nay.kirill.bluetooth.server.impl

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import android.content.Context
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nay.kirill.bluetooth.server.exceptions.ServerException
import nay.kirill.bluetooth.utils.CharacteristicConstants
import no.nordicsemi.android.ble.BleManager
import no.nordicsemi.android.ble.BleServerManager
import no.nordicsemi.android.ble.observer.ServerObserver

class ServerManager(
        private val context: Context,
        private val consumerCallback: ServerConsumerCallback
) : BleServerManager(context), ServerObserver {

    private val gattCharacteristic = sharedCharacteristic(
            CharacteristicConstants.CHARACTERISTIC_UUID,
            BluetoothGattCharacteristic.PROPERTY_READ
                    or BluetoothGattCharacteristic.PROPERTY_WRITE
                    or BluetoothGattCharacteristic.PROPERTY_NOTIFY,
            BluetoothGattCharacteristic.PERMISSION_READ or BluetoothGattCharacteristic.PERMISSION_WRITE,
            descriptor(
                    CharacteristicConstants.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID,
                    BluetoothGattDescriptor.PERMISSION_READ
                            or BluetoothGattDescriptor.PERMISSION_WRITE, byteArrayOf(0, 0)
            ),
            description("A characteristic to be read", true) // descriptors
    )

    private val gattService = service(
            CharacteristicConstants.SERVICE_UUID,
            gattCharacteristic
    )

    private val serverConnections = mutableMapOf<String, DeviceConnectionManager>()

    override fun initializeServer(): MutableList<BluetoothGattService> {
        setServerObserver(this)

        return mutableListOf(gattService)
    }

    override fun onServerReady() {
        consumerCallback.onServerReady()
    }

    override fun onDeviceConnectedToServer(device: BluetoothDevice) {
        serverConnections[device.address] = DeviceConnectionManager(context)
                .apply {
                    useServer(this@ServerManager)

                    connect(device)
                            .fail { _, status ->
                                consumerCallback.onFailure(ServerException.DeviceConnectionException(status))
                            }
                            .done { connectedDevice ->
                                consumerCallback.onNewDeviceConnected(connectedDevice, serverConnections.size + 1)

                                // Pretty dumb huck of delaying sending message containing device address
                                // At that time client is able to enable notification
                                GlobalScope.launch {
                                    delay(2000)
                                    sendMessage("address${device.address}".toByteArray(), gattCharacteristic)
                                }
                            }
                            .enqueue()
                }
    }

    override fun onDeviceDisconnectedFromServer(device: BluetoothDevice) {
        consumerCallback.onDeviceDisconnected(device, serverConnections.size - 1)

        serverConnections.remove(device.address)?.close()
    }

    fun sendMessage(message: ByteArray, deviceId: String?) {
        if (deviceId == null) {
            serverConnections.forEach { it.value.sendMessage(message, gattCharacteristic) }
        } else {
            serverConnections[deviceId]?.sendMessage(message, gattCharacteristic)
        }
    }

    private inner class DeviceConnectionManager(
            context: Context
    ) : BleManager(context) {

        override fun getGattCallback(): BleManagerGattCallback = GattCallback()

        private inner class GattCallback : BleManagerGattCallback() {

            override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean = true

            override fun onServicesInvalidated() {
                /* Do not need any service from connected device */
            }

            override fun initialize() {
                requestMtu(517).enqueue()

                setWriteCallback(gattCharacteristic).with { device, data ->
                    if (data.value != null) {
                        consumerCallback.onNewMessage(device, data.value!!, serverConnections.size)
                    }
                }
            }

        }

        fun sendMessage(value: ByteArray, characteristic: BluetoothGattCharacteristic) {
            sendNotification(characteristic, value)
                    .fail { _, status ->
                        consumerCallback.onFailure(ServerException.SendMessageException(status))
                    }
                    .enqueue()
        }

    }

}
