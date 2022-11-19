package nay.kirill.bluetooth.scanner.api

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import no.nordicsemi.android.support.v18.scanner.ScanResult

data class BluetoothScannedDevice(
        val name: String?,
        val bluetoothDevice: BluetoothDevice
) {

    @SuppressLint("MissingPermission")
    constructor(scanResult: ScanResult) : this(
            name = scanResult.scanRecord?.deviceName
                ?: scanResult.device.name
                ?: scanResult.device.address,
            bluetoothDevice = scanResult.device
    )

}