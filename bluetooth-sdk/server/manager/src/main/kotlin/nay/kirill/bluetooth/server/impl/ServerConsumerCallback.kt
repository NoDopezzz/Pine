package nay.kirill.bluetooth.server.impl

import nay.kirill.bluetooth.server.exceptions.ServerException

interface ServerConsumerCallback {

    fun onFailure(throwable: ServerException)

}