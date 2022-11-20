package nay.kirill.pine.naturalist.impl.presentation.chat

import nay.kirill.pine.naturalist.impl.domain.ChatMessage

internal sealed interface ChatState {

    data class Content(
            val messages: List<ChatMessage>,
            val enteredMessage: String
    ) : ChatState {

        companion object {

            fun initial() = Content(
                    messages = listOf(),
                    enteredMessage = ""
            )

        }

    }

    object Loading: ChatState

    object Error : ChatState

}

internal sealed interface ChatUiState {

    data class Content(
            val enteredMessage: String = ""
    ) : ChatUiState

    object Loading: ChatUiState

    object Error : ChatUiState

}