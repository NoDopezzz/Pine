package nay.kirill.pine.naturalist.impl.domain

data class ChatMessage(
        val id: Int,
        val deviceAddress: String,
        val text: String,
        val time: String
)