package nay.kirill.pine.naturalist.impl.presentation.chat

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import nay.kirill.bluetooth.client.ClientConsumerCallback
import nay.kirill.bluetooth.client.ClientManager
import nay.kirill.bluetooth.client.exceptions.ClientException
import nay.kirill.core.arch.BaseViewModel
import nay.kirill.pine.naturalist.impl.presentation.NaturalistNavigation
import nay.kirill.pine.naturalist.impl.presentation.entername.EnterNameArgs

@SuppressLint("StaticFieldLeak") // Provide application context to viewModel
internal class ChatViewModel(
        args: ChatArgs,
        chatStateConverter: ChatStateConverter,
        private val navigation: NaturalistNavigation,
        private val context: Context
) : BaseViewModel<ChatState, ChatUiState>(
        converter = chatStateConverter,
        initialState = ChatState.Loading
) {

    private val callback = object : ClientConsumerCallback {

        override fun onNewMessage(device: BluetoothDevice, message: ByteArray) {

        }

        override fun onFailure(throwable: ClientException) {
            onError()
        }

    }

    private val clientManager: ClientManager = ClientManager(context, callback)

    init {
        clientManager.connect(args.device)
                .retry(4, 150)
                .useAutoConnect(false)
                .done {
                    state = ChatState.Content.initial()
                }
                .fail { _, status ->
                    onError()
                }
                .enqueue()
    }

    private fun onError() {
        state = ChatState.Error
        clientManager.close()
    }

    fun back() {
        navigation.back()
    }

    fun retry() {
        navigation.openConnection()
    }

    fun messageEnter(value: String) {
        state = successState { copy(enteredMessage = value) }
    }

    fun editName() {
        navigation.openEnterName(args = EnterNameArgs.Edit)
    }

    fun sendMessage() {
        state = successState { copy(enteredMessage = "") }
    }

    override fun onCleared() {
        super.onCleared()

        clientManager.close()
    }

    private fun successState(update: ChatState.Content.() -> ChatState): ChatState = (state as? ChatState.Content)?.let(update) ?: state

}
