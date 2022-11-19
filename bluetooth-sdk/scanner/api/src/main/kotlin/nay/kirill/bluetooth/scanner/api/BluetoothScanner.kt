package nay.kirill.bluetooth.scanner.api

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.flow.Flow

interface BluetoothScanner {

    suspend fun getScannedDevicesFlow(): Flow<Result<List<BluetoothScannedDevice>>>

}
