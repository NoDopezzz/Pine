package nay.kirill.bluetooth.client.exceptions

sealed class ClientException : Throwable() {

    class SubscriptionConnection(val status: Int) : ClientException()

    class ConnectionException(val status: Int) : ClientException()

    class ReadCharacteristicException(val status: Int) : ClientException()

    class WriteCharacteristicException(val status: Int) : ClientException()

    class ServiceInvalidated() : ClientException()

    class UnknownException() : ClientException()

}