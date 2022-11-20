package nay.kirill.pine.naturalist.impl.domain

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

@Serializable
data class ChatMessage(
        val deviceAddress: String,
        val text: String,
        val name: String?
)

fun ByteArray?.toMessages(): List<ChatMessage> = when {
    this == null || this.isEmpty() -> listOf()
    else -> try {
        Json.decodeFromString(String(this))
    } catch (e: Throwable) {
        listOf()
    }
}

fun List<ChatMessage>.toByteArray(): ByteArray = Json.encodeToString(this).toByteArray()
