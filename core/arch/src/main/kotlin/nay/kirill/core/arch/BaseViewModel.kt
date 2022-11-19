package nay.kirill.core.arch

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<TState, TUiState>(
    private val converter: (TState) -> TUiState,
    initialState: TState
) : ViewModel() {

    protected var state = initialState
        protected set(value) {
            uiState.value = converter(value)
            field = value
        }

    val uiState = mutableStateOf(state.run(converter))

}
