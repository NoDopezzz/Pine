package nay.kirill.pine.naturalist.impl.presentation.chat

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import nay.kirill.bluetooth.client.ClientConsumerCallback
import nay.kirill.bluetooth.client.ClientManager
import nay.kirill.bluetooth.client.exceptions.ClientException
import nay.kirill.bluetooth.messages.ChatMessage
import nay.kirill.bluetooth.messages.toByteArray
import nay.kirill.bluetooth.messages.toMessage
import nay.kirill.bluetooth.messages.toMessages
import nay.kirill.core.arch.BaseViewModel
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

            clientManager.readMessages()
                    .onSuccess { array ->
                        state = ChatState.Content(
                                messages = array.toMessages(),
                                enteredMessage = "",
                                deviceId = context.dataStore.data.map { it[UserDataStoreKey.USER_ID] }.firstOrNull().orEmpty()
                        )
                    }
                    .onFailure {
                        state = ChatState.Error
                    }
        }

        clientManager.getMessageFlow()
                .filterNotNull()
                .onEach { result ->
                    state = successState {
                        copy(messages = messages.plus(result.toMessage()))
                    }
                }
                .launchIn(viewModelScope)
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
                val message = ChatMessage(
                        deviceAddress = context.dataStore.data.map { it[UserDataStoreKey.USER_ID] }.firstOrNull().orEmpty(),
                        text = contentState.enteredMessage,
                        name = context.dataStore.data.map { it[UserDataStoreKey.USER_NAME] }.firstOrNull()
                )

                clientManager.sendMessage(message.toByteArray())

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
