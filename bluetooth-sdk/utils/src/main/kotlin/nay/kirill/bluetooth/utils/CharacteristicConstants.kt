package nay.kirill.bluetooth.utils

import java.util.UUID

object CharacteristicConstants {

    val SERVICE_UUID: UUID = UUID.fromString("cf4caeae-68b1-11ed-9022-0242ac120002")

    val CHAT_CHARACTERISTIC_UUID: UUID = UUID.fromString("8af0c694-63e5-11ed-81ce-0242ac120002")

    val SEND_MESSAGE_CHARACTERISTIC_UUID: UUID = UUID.fromString("92387144-68be-11ed-9022-0242ac120002")

    val NOTIFY_CHARACTERISTIC_UUID: UUID = UUID.fromString("c3db48aa-68b1-11ed-9022-0242ac120002")

    val CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID: UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

}
