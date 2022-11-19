package nay.kirill.bluetooth.server.exceptions

sealed class ServerException : Throwable() {

    class DeviceConnectionException(val status: Int) : ServerException()

    class SendMessageException(val status: Int) : ServerException()

    class UnknownException(override val message: String? = null) : ServerException()

}