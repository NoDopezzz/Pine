package nay.kirill.pine.naturalist.impl.presentation.chat

import nay.kirill.pine.naturalist.impl.presentation.chat.models.ChatMessageUiModel

internal class ChatStateConverter : (ChatState) -> ChatUiState {

    override fun invoke(state: ChatState): ChatUiState = when (state) {
        is ChatState.Error -> ChatUiState.Error
        is ChatState.Content -> ChatUiState.Content(
                enteredMessage = state.enteredMessage,
                messages = state.messages.map { message ->
                    ChatMessageUiModel(
                            text = message.text,
                            name = message.name,
                            isMine = message.deviceAddress == state.deviceId
                    )
                }
        )
        is ChatState.Loading -> ChatUiState.Loading
    }
}