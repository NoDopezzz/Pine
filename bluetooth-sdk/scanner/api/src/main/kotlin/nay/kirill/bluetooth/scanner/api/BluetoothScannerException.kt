package nay.kirill.bluetooth.scanner.api

data class BluetoothScannerException(override val message: String) : Exception(message)