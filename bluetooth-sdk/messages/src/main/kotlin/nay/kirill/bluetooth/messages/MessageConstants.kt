package nay.kirill.bluetooth.messages

sealed class Message {

    companion object {

        fun toByteArray(message: Message): ByteArray = TODO()

        fun fromByteArray(byteArray: ByteArray): Message = TODO()

    }

}
