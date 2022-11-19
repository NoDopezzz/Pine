package nay.kirill.bluetooth.utils

import java.util.UUID

object CharacteristicConstants {

    val SERVICE_UUID: UUID = UUID.fromString("812d777e-63e5-11ed-81ce-0242ac120002")

    val CHARACTERISTIC_UUID: UUID = UUID.fromString("8af0c694-63e5-11ed-81ce-0242ac120002")

    val CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID: UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

}

object MessageConstants {

    const val START = "START"

}