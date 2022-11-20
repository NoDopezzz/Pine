package nay.kirill.bluetooth.client

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import android.util.Log
import kotlinx.coroutines.supervisorScope
import nay.kirill.bluetooth.client.exceptions.ClientException
import nay.kirill.bluetooth.utils.CharacteristicConstants.CHARACTERISTIC_UUID
import nay.kirill.bluetooth.utils.CharacteristicConstants.SERVICE_UUID
import no.nordicsemi.android.ble.BleManager
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ClientManager(
        appContext: Context,
        private val consumerCallback: ClientConsumerCallback
) : BleManager(appContext) {

    private var characteristic: BluetoothGattCharacteristic? = null

    override fun getGattCallback() = object : BleManagerGattCallback() {

        override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
            val service = gatt.getService(SERVICE_UUID) ?: return false

            characteristic = service.getCharacteristic(CHARACTERISTIC_UUID)
            val properties = characteristic?.properties ?: 0

            return (properties and BluetoothGattCharacteristic.PROPERTY_READ != 0) &&
                    (properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0)
        }

        override fun onServicesInvalidated() {
            consumerCallback.onFailure(ClientException.ServiceInvalidated())
            characteristic = null
        }

        override fun initialize() {
            requestMtu(517).enqueue()

            setNotificationCallback(characteristic).with { device, data ->
                if (data.value != null) {
                    updateDeviceAddress(data.value!!)
                    consumerCallback.onNewMessage(device, data.value!!)
                }
            }

            beginAtomicRequestQueue()
                    .add(enableNotifications(characteristic)
                            .fail { _: BluetoothDevice?, status: Int ->
                                consumerCallback.onFailure(ClientException.ConnectionException(status))
                                disconnect().enqueue()
                            }
                    )
                    .fail { _, _ ->
                        consumerCallback.onFailure(ClientException.UnknownException())
                    }
                    .enqueue()
        }

        // Updating device address fetched from the server
        private fun updateDeviceAddress(value: ByteArray) {
            String(value).apply {
                if (contains("address")) {
                    ClientConfig.deviceAddress = removeRange(0, 7)
                }
            }
        }
    }

    suspend fun connectWithServer(device: BluetoothDevice): Result<BluetoothDevice> = suspendCoroutine { continuation ->
        connect(device)
                .retry(4, 150)
                .useAutoConnect(false)
                .done { connectedDevice ->
                    continuation.resume(Result.success(connectedDevice))
                }
                .fail { _, status ->
                    continuation.resume(Result.failure(ClientException.ConnectionException(status)))
                }
                .enqueue()
    }

    suspend fun readMessages(): Result<ByteArray?> = suspendCoroutine { continuation ->
        readCharacteristic(characteristic)
                .with { _, data ->
                    continuation.resume(Result.success(data.value))
                }
                .fail { _, status ->
                    continuation.resume(Result.failure(ClientException.ReadCharacteristicException(status)))
                }
                .enqueue()
    }

    suspend fun sendMessage(message: ByteArray): Result<Boolean> = suspendCoroutine { continuation ->
        writeCharacteristic(characteristic, message, BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT)
                .done {
                    continuation.resume(Result.success(true))
                }
                .fail { _, status ->
                    continuation.resume(Result.failure(ClientException.WriteCharacteristicException(status)))
                }
                .enqueue()
    }
}