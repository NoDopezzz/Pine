package nay.kirill.core.utils.callbackFlow

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

open class CallbackFlow<T> {

    val result: Flow<T>
        get() = channel.receiveAsFlow()

    private val channel = Channel<T>()

    fun setResult(value: T) = channel.trySend(value)

}