package nay.kirill.pine.naturalist.impl.presentation.chat

import nay.kirill.bluetooth.messages.ChatMessage
import nay.kirill.pine.naturalist.impl.presentation.chat.models.ChatMessageUiModel

internal sealed interface ChatState {

    data class Content(
            val messages: List<ChatMessage>,
            val enteredMessage: String,
            val deviceId: String
    ) : ChatState

    object Loading: ChatState

    object Error : ChatState

}

internal sealed interface ChatUiState {

    data class Content(
            val enteredMessage: String = "",
            val messages: List<ChatMessageUiModel>
    ) : ChatUiState

    object Loading: ChatUiState

    object Error : ChatUiState

}