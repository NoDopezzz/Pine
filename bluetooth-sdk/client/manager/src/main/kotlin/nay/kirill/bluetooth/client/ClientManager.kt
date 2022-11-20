package nay.kirill.bluetooth.client

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import nay.kirill.bluetooth.client.exceptions.ClientException
import nay.kirill.bluetooth.utils.CharacteristicConstants.CHAT_CHARACTERISTIC_UUID
import nay.kirill.bluetooth.utils.CharacteristicConstants.NOTIFY_CHARACTERISTIC_UUID
import nay.kirill.bluetooth.utils.CharacteristicConstants.SEND_MESSAGE_CHARACTERISTIC_UUID
import nay.kirill.bluetooth.utils.CharacteristicConstants.SERVICE_UUID
import no.nordicsemi.android.ble.BleManager
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ClientManager(
        appContext: Context,
        private val consumerCallback: ClientConsumerCallback
) : BleManager(appContext) {

    private var chatCharacteristic: BluetoothGattCharacteristic? = null

    private var notifyMessageCharacteristic: BluetoothGattCharacteristic? = null

    private var sendMessageCharacteristic: BluetoothGattCharacteristic? = null

    private val readMessageFlow: MutableSharedFlow<ByteArray?> = MutableSharedFlow(
            replay = 1,
            extraBufferCapacity = 1
    )

    override fun getGattCallback() = object : BleManagerGattCallback() {

        override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
            val service = gatt.getService(SERVICE_UUID) ?: return false

            chatCharacteristic = service.getCharacteristic(CHAT_CHARACTERISTIC_UUID)
            val properties = chatCharacteristic?.properties ?: 0

            notifyMessageCharacteristic = service.getCharacteristic(NOTIFY_CHARACTERISTIC_UUID)

            sendMessageCharacteristic = service.getCharacteristic(SEND_MESSAGE_CHARACTERISTIC_UUID)

            return properties and BluetoothGattCharacteristic.PROPERTY_READ != 0
        }

        override fun onServicesInvalidated() {
            consumerCallback.onFailure(ClientException.ServiceInvalidated())
            chatCharacteristic = null
            notifyMessageCharacteristic = null
            sendMessageCharacteristic = null
        }

        override fun initialize() {
            requestMtu(517).enqueue()

            setNotificationCallback(notifyMessageCharacteristic).with { device, data ->
                if (data.value != null) {
                    Log.i("ClientManager", "new data: ${String(data.value!!)}")
                    readMessageFlow.tryEmit(data.value!!)
                }
            }

            beginAtomicRequestQueue()
                    .add(enableNotifications(notifyMessageCharacteristic)
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

    fun getMessageFlow(): Flow<ByteArray?> = readMessageFlow.asSharedFlow()

    suspend fun readMessages(): Result<ByteArray?> = suspendCoroutine { continuation ->
        readCharacteristic(chatCharacteristic)
                .with { _, data ->
                    continuation.resume(Result.success(data.value))
                }
                .fail { _, status ->
                    continuation.resume(Result.failure(ClientException.ReadCharacteristicException(status)))
                }
                .enqueue()
    }

    suspend fun sendMessage(message: ByteArray): Result<Boolean> = suspendCoroutine { continuation ->
        writeCharacteristic(sendMessageCharacteristic, message, BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE)
                .split()
                .done {
                    continuation.resume(Result.success(true))
                }
                .fail { _, status ->
                    continuation.resume(Result.failure(ClientException.WriteCharacteristicException(status)))
                }
                .enqueue()
    }
}