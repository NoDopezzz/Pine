package nay.kirill.pine.naturalist.impl.presentation.chat

internal class ChatStateConverter : (ChatState) -> ChatUiState {

    override fun invoke(state: ChatState): ChatUiState = when (state) {
        is ChatState.Error -> ChatUiState.Error
        is ChatState.Content -> ChatUiState.Content(
                enteredMessage = state.enteredMessage
        )
        is ChatState.Loading -> ChatUiState.Loading
    }
}