package nay.kirill.pine.naturalist.impl.presentation.chat.models

data class ChatMessageUiModel(
        val text: String,
        val name: String?,
        val isMine: Boolean
)