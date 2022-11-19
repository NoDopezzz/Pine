package nay.kirill.bluetooth.client.callback.message

import nay.kirill.bluetooth.messages.Message

sealed interface ClientMessage {

    data class SendMessage(val message: Message) : ClientMessage

}
