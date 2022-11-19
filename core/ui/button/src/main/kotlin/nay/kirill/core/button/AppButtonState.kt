package nay.kirill.core.button

sealed interface AppButtonState {

    object Loading : AppButtonState

    data class Content(val text: String) : AppButtonState

}
