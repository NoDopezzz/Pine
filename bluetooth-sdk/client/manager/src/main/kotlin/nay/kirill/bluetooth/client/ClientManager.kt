package nay.kirill.bluetooth.client

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import nay.kirill.bluetooth.client.exceptions.ClientException
import nay.kirill.bluetooth.utils.CharacteristicConstants.CHAT_CHARACTERISTIC_UUID
import nay.kirill.bluetooth.utils.CharacteristicConstants.DEVICE_CHARACTERISTIC_UUID
import nay.kirill.bluetooth.utils.CharacteristicConstants.SERVICE_UUID
import no.nordicsemi.android.ble.BleManager
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ClientManager(
        appContext: Context,
        private val consumerCallback: ClientConsumerCallback
) : BleManager(appContext) {

    private var chatCharacteristic: BluetoothGattCharacteristic? = null

    private var updateNotifyCharacteristic: BluetoothGattCharacteristic? = null

    private val readMessageFlow: MutableSharedFlow<Result<ByteArray?>> = MutableSharedFlow(
            replay = 1,
            extraBufferCapacity = 1
    )

    override fun getGattCallback() = object : BleManagerGattCallback() {

        override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
            val service = gatt.getService(SERVICE_UUID) ?: return false

            chatCharacteristic = service.getCharacteristic(CHAT_CHARACTERISTIC_UUID)
            val properties = chatCharacteristic?.properties ?: 0

            updateNotifyCharacteristic = service.getCharacteristic(DEVICE_CHARACTERISTIC_UUID)

            return (properties and BluetoothGattCharacteristic.PROPERTY_READ != 0) &&
                    (properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0)
        }

        override fun onServicesInvalidated() {
            consumerCallback.onFailure(ClientException.ServiceInvalidated())
            chatCharacteristic = null
            updateNotifyCharacteristic = null
        }

        override fun initialize() {
            requestMtu(517).enqueue()

            setNotificationCallback(chatCharacteristic).with { device, data ->
                if (data.value != null) {
                    consumerCallback.onNewMessage(device, data.value!!)
                }
            }

            setNotificationCallback(updateNotifyCharacteristic).with { device, data ->
                readMessages()
            }

            beginAtomicRequestQueue()
                    .add(enableNotifications(updateNotifyCharacteristic)
                            .fail { _: BluetoothDevice?, status: Int ->
                                consumerCallback.onFailure(ClientException.ConnectionException(status))
                                disconnect().enqueue()
                            }
                    )
                    .fail { _, _ ->
                        consumerCallback.onFailure(ClientException.UnknownException())
                    }
                    .enqueue()

            beginAtomicRequestQueue()
                    .add(enableNotifications(chatCharacteristic)
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

    fun getMessageFlow(): Flow<Result<ByteArray?>> {
        readMessages()

        return readMessageFlow.asSharedFlow()
    }

    private fun readMessages() {
        readCharacteristic(chatCharacteristic)
                .with { _, data ->
                    Log.i("ClientManager", "read up some info: ${String(data.value!!)}")
                    readMessageFlow.tryEmit(Result.success(data.value))
                }
                .fail { _, status ->
                    readMessageFlow.tryEmit(Result.failure(ClientException.ReadCharacteristicException(status)))
                }
                .enqueue()
    }

    suspend fun sendMessage(message: ByteArray): Result<Boolean> = suspendCoroutine { continuation ->
        writeCharacteristic(chatCharacteristic, message, BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT)
                .done {
                    continuation.resume(Result.success(true))
                }
                .fail { _, status ->
                    continuation.resume(Result.failure(ClientException.WriteCharacteristicException(status)))
                }
                .enqueue()
    }
}