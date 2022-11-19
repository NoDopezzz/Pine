package nay.kirill.core.arch

sealed class ContentEvent<out T>(open val data: T?) {

    fun isLoading() = this is Loading

    fun isError() = this is Error

    fun isSuccess() = this is Success

    fun isComplete() = this is Complete

    fun toComplete() = Complete(data)

    fun toLoading() = Loading(data)

    fun toError(throwable: Throwable) = Error(throwable, data)

    operator fun plus(throwable: Throwable) = toError(throwable)

    fun <R> copy(newData: R): ContentEvent<R> = when (this) {
        is Success -> Success(newData)
        is Loading -> Loading(newData)
        is Error -> Error(throwable, newData)
        is Complete -> Complete(newData)
    }

    inline fun onSuccess(action: Success<T>.() -> Unit) {
        if (this is Success) {
            action.invoke(this)
        }
    }

    inline fun onComplete(action: Complete<T>.() -> Unit) {
        if (this is Complete) {
            action.invoke(this)
        }
    }

    inline fun onError(action: Error<T>.() -> Unit) {
        if (this is Error) {
            action.invoke(this)
        }
    }

    inline fun onLoading(action: Loading<T>.() -> Unit) {
        if (this is Loading) {
            action.invoke(this)
        }
    }

    data class Loading<out T>(override val data: T? = null) : ContentEvent<T>(data)

    data class Success<out T>(override val data: T) : ContentEvent<T>(data)

    data class Error<out T>(val throwable: Throwable, override val data: T? = null) : ContentEvent<T>(data)

    data class Complete<out T>(override val data: T? = null) : ContentEvent<T>(data)

}
