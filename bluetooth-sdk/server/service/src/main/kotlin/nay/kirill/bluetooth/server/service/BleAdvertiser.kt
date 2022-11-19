package nay.kirill.bluetooth.server.service

import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.os.ParcelUuid
import nay.kirill.bluetooth.utils.CharacteristicConstants

object BleAdvertiser {

	class Callback : AdvertiseCallback()

	fun settings(): AdvertiseSettings {
		return AdvertiseSettings.Builder()
				.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
				.setConnectable(true)
				.setTimeout(0)
				.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_LOW)
				.build()
	}

	fun advertiseData(): AdvertiseData {
		return AdvertiseData.Builder()
				.setIncludeDeviceName(false) // Including it will blow the length
				.setIncludeTxPowerLevel(false)
				.addServiceUuid(ParcelUuid(CharacteristicConstants.SERVICE_UUID))
				.build()
	}
}