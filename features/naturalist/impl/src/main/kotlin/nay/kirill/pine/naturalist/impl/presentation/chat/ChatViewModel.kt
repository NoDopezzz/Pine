package nay.kirill.pine.naturalist.impl.presentation.chat

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import nay.kirill.bluetooth.client.ClientConfig
import nay.kirill.bluetooth.client.ClientConsumerCallback
import nay.kirill.bluetooth.client.ClientManager
import nay.kirill.bluetooth.client.exceptions.ClientException
import nay.kirill.core.arch.BaseViewModel
import nay.kirill.pine.naturalist.impl.domain.ChatMessage
import nay.kirill.pine.naturalist.impl.domain.toByteArray
import nay.kirill.pine.naturalist.impl.domain.toMessages
import nay.kirill.pine.naturalist.impl.presentation.NaturalistNavigation
import nay.kirill.pine.naturalist.impl.presentation.entername.EnterNameArgs
import nay.kirill.pine.naturalist.impl.presentation.entername.UserDataStoreKey
import nay.kirill.pine.naturalist.impl.presentation.entername.dataStore

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
        viewModelScope.launch {
            clientManager.connectWithServer(device = args.device)
                    .onFailure {
                        state = ChatState.Error
                    }

            clientManager.getMessageFlow()
                    .onEach { result ->
                        result
                                .onSuccess { data ->
                                    when (state) {
                                        is ChatState.Content -> state = successState { copy(messages = data.toMessages()) }
                                        is ChatState.Loading -> state = ChatState.Content(
                                                messages = data.toMessages(),
                                                enteredMessage = ""
                                        )
                                        else -> Unit
                                    }

                                }
                                .onFailure {
                                    state = ChatState.Error
                                }
                    }
                    .launchIn(viewModelScope)
        }
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
        if ((state as? ChatState.Content)?.enteredMessage.isNullOrEmpty()) return

        viewModelScope.launch {
            (state as? ChatState.Content)?.let { contentState ->
                val updatedMessages = contentState.messages.plus(
                        ChatMessage(
                                deviceAddress = ClientConfig.deviceAddress.orEmpty(),
                                text = contentState.enteredMessage,
                                name = context.dataStore.data.map { it[UserDataStoreKey.USER_NAME] }.firstOrNull()
                        )
                )

                clientManager.sendMessage(updatedMessages.toByteArray())

                state = contentState.copy(enteredMessage = "")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        clientManager.close()
    }

    private fun successState(update: ChatState.Content.() -> ChatState): ChatState = (state as? ChatState.Content)?.let(update) ?: state

}
