package nay.kirill.bluetooth.server.impl

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import android.content.Context
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
            CharacteristicConstants.CHAT_CHARACTERISTIC_UUID,
            BluetoothGattCharacteristic.PROPERTY_READ
                    or BluetoothGattCharacteristic.PROPERTY_WRITE
                    or BluetoothGattCharacteristic.PROPERTY_NOTIFY,
            BluetoothGattCharacteristic.PERMISSION_READ or BluetoothGattCharacteristic.PERMISSION_WRITE,
            descriptor(
                    CharacteristicConstants.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID,
                    BluetoothGattDescriptor.PERMISSION_READ
                            or BluetoothGattDescriptor.PERMISSION_WRITE, byteArrayOf(0, 0)
            ),
            description("A characteristic of chat messages", true) // descriptors
    )

    private val changeNotifyCharacteristic = sharedCharacteristic(
            CharacteristicConstants.DEVICE_CHARACTERISTIC_UUID,
            BluetoothGattCharacteristic.PROPERTY_READ
                    or BluetoothGattCharacteristic.PROPERTY_NOTIFY,
            BluetoothGattCharacteristic.PERMISSION_READ or BluetoothGattCharacteristic.PERMISSION_WRITE,
            descriptor(
                    CharacteristicConstants.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID,
                    BluetoothGattDescriptor.PERMISSION_READ
                            or BluetoothGattDescriptor.PERMISSION_WRITE, byteArrayOf(0, 0)
            ),
            description("A characteristic for transferring device address", true) // descriptors
    )

    private val gattService = service(
            CharacteristicConstants.SERVICE_UUID,
            gattCharacteristic,
            changeNotifyCharacteristic
    )

    private val serverConnections = mutableMapOf<String, DeviceConnectionManager>()

    override fun initializeServer(): MutableList<BluetoothGattService> {
        setServerObserver(this)

        return mutableListOf(gattService)
    }

    override fun onServerReady() {

    }

    override fun onDeviceConnectedToServer(device: BluetoothDevice) {
        serverConnections[device.address] = DeviceConnectionManager(context)
                .apply {
                    useServer(this@ServerManager)

                    connect(device)
                            .fail { _, status ->
                                consumerCallback.onFailure(ServerException.DeviceConnectionException(status))
                            }
                            .enqueue()
                }
    }

    override fun onDeviceDisconnectedFromServer(device: BluetoothDevice) {
        serverConnections.remove(device.address)?.close()
    }

    fun notifyUpdate() {
        serverConnections.forEach { it.value.sendMessage("update".toByteArray(), changeNotifyCharacteristic) }
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
                        notifyUpdate()
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
