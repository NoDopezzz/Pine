package nay.kirill.bluetooth.server.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.messages.Message
import nay.kirill.bluetooth.server.callback.event.ServerEvent
import nay.kirill.bluetooth.server.callback.event.ServerEventCallback
import nay.kirill.bluetooth.server.callback.message.ServerMessage
import nay.kirill.bluetooth.server.callback.message.ServerMessageCallback
import nay.kirill.bluetooth.server.exceptions.ServerException
import nay.kirill.bluetooth.server.impl.ServerConsumerCallback
import nay.kirill.bluetooth.server.impl.ServerManager
import nay.kirill.core.utils.permissions.PermissionsUtils
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

/**
 * Foreground service that enables BLE server.
 * Before binding service make sure to declare it in Manifest
 * and request for [Manifest.permission.BLUETOOTH_ADVERTISE] if your are targeting [Build.VERSION_CODES.S]
 */
class BleServerService : Service(), CoroutineScope {

    override val coroutineContext: CoroutineContext by lazy {
        SupervisorJob()
    }

    private var serverManager: ServerManager? = null

    private val serverEventCallback: ServerEventCallback by inject()

    private val serverMessageCallback: ServerMessageCallback by inject()

    private val consumerCallback = object : ServerConsumerCallback {

        override fun onNewDeviceConnected(device: BluetoothDevice, deviceCount: Int) {
            serverEventCallback.setResult(ServerEvent.OnDeviceConnected(device, deviceCount))
        }

        override fun onDeviceDisconnected(device: BluetoothDevice, deviceCount: Int) {
            serverEventCallback.setResult(ServerEvent.OnDeviceDisconnected(device,deviceCount))
        }

        override fun onServerReady() {
            serverEventCallback.setResult(ServerEvent.OnServerIsReady)
        }

        override fun onNewMessage(device: BluetoothDevice, message: ByteArray, deviceCount: Int) {
            serverEventCallback.setResult(ServerEvent.OnNewMessage(Message.fromByteArray(message), device, deviceCount))
        }

        override fun onFailure(throwable: ServerException) {
            serverEventCallback.setResult(ServerEvent.OnMinorException(throwable))
        }

    }

    private val bleAdvertiseCallback = BleAdvertiser.Callback()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val notificationChannel = NotificationChannel(
                    BleServerService::class.java.simpleName,
                    resources.getString(R.string.server_service_name),
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                    .createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(this, BleServerService::class.java.simpleName)
                .setContentTitle(resources.getString(R.string.server_service_name))
                .setContentText(resources.getString(R.string.server_service_notification))
                .setAutoCancel(true)
                .build()

        startForeground(1, notification)

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        if (bluetoothManager.adapter?.isEnabled == true) startServerService()

        serverMessageCallback.result
                .onEach { msg ->
                    when (msg) {
                        is ServerMessage.WriteCharacteristic -> sendMessage(msg.message, msg.deviceAddress)
                        else -> Unit
                    }
                }
                .launchIn(this)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        stopServerService()

        coroutineContext.cancelChildren()
        super.onDestroy()
    }

    private fun startServerService() {
        try {
            if (PermissionsUtils.checkBluetoothAdvertisePermission(this)) {
                serverManager = ServerManager(this, consumerCallback)
                serverManager?.open()

                val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
                bluetoothManager.adapter.bluetoothLeAdvertiser?.startAdvertising(
                        BleAdvertiser.settings(),
                        BleAdvertiser.advertiseData(),
                        bleAdvertiseCallback
                )
            }
        } catch (e: Throwable) {
            serverEventCallback.setResult(ServerEvent.OnFatalException(ServerException.UnknownException(e.message)))

            stopSelf()
        }
    }

    private fun stopServerService() {
        try {
            if (PermissionsUtils.checkBluetoothAdvertisePermission(this)) {
                serverManager?.close()
                serverManager = null

                val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
                bluetoothManager.adapter.bluetoothLeAdvertiser?.stopAdvertising(bleAdvertiseCallback)
            }
        } catch (e: Throwable) {
            serverEventCallback.setResult(ServerEvent.OnFatalException(ServerException.UnknownException(e.message)))
        }
    }

    private fun sendMessage(message: Message, deviceId: String?) {
        serverManager?.sendMessage(Message.toByteArray(message), deviceId)
    }
}