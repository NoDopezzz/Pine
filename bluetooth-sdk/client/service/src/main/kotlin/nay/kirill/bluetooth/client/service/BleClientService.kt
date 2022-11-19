package nay.kirill.bluetooth.client.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.client.ClientConsumerCallback
import nay.kirill.bluetooth.client.ClientManager
import nay.kirill.bluetooth.client.callback.event.ClientEvent
import nay.kirill.bluetooth.client.callback.event.ClientEventCallback
import nay.kirill.bluetooth.client.callback.message.ClientMessage
import nay.kirill.bluetooth.client.callback.message.ClientMessageCallback
import nay.kirill.bluetooth.client.exceptions.ClientException
import nay.kirill.bluetooth.messages.Message
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

class BleClientService : Service(), CoroutineScope {

    override val coroutineContext: CoroutineContext by lazy {
        SupervisorJob()
    }

    private val eventCallback: ClientEventCallback by inject()

    private val messageCallback: ClientMessageCallback by inject()

    private var clientManager: ClientManager? = null

    private val consumerCallback = object : ClientConsumerCallback {

        override fun onNewMessage(device: BluetoothDevice, message: ByteArray) {
            eventCallback.setResult(ClientEvent.OnNewMessage(Message.fromByteArray(message)))
        }

        override fun onFailure(throwable: ClientException) {
            eventCallback.setResult(ClientEvent.OnFailure(throwable))
        }

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notificationChannel = NotificationChannel(
                BleClientService::class.java.simpleName,
                resources.getString(R.string.client_service_name),
                NotificationManager.IMPORTANCE_DEFAULT
        )
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(notificationChannel)

        val notification = NotificationCompat.Builder(this, BleClientService::class.java.simpleName)
                .setContentTitle(resources.getString(R.string.client_service_name))
                .setContentText(resources.getString(R.string.client_service_notification))
                .setAutoCancel(true)
                .build()

        startForeground(1, notification)

        val device = intent.getParcelableExtra<BluetoothDevice>(BLUETOOTH_DEVICE_EXTRA)
        if (device != null) startClientService(device)

        messageCallback.result
                .onEach { reduceMessage(it) }
                .launchIn(this)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopClientService()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    private fun reduceMessage(msg: ClientMessage) {
        when (msg) {
            is ClientMessage.SendMessage -> sendMessage(msg.message)
        }
    }

    private fun sendMessage(message: Message) {
        clientManager?.sendMessage(Message.toByteArray(message))
    }

    private fun startClientService(device: BluetoothDevice) {
        clientManager = ClientManager(this, consumerCallback)
        clientManager
                ?.connect(device)
                ?.retry(4, 150)
                ?.useAutoConnect(false)
                ?.done {
                    eventCallback.setResult(ClientEvent.ConnectionSuccess(it))
                }
                ?.fail { _, status ->
                    eventCallback.setResult(
                            value = ClientEvent.OnFailure(ClientException.ConnectionException(status))
                    )
                }
                ?.enqueue()
    }

    private fun stopClientService() {
        clientManager?.close()
        clientManager = null
    }

    companion object {

        const val BLUETOOTH_DEVICE_EXTRA = "BLUETOOTH_DEVICE_EXTRA"

    }
}
