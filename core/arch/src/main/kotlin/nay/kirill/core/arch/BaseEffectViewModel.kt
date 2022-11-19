package nay.kirill.core.arch

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseEffectViewModel<TState, TUiState, TEffect>(
        converter: (TState) -> TUiState,
        initialState: TState
) : BaseViewModel<TState, TUiState>(converter, initialState) {

    protected val _effect: Channel<TEffect> = Channel()
    val effect = _effect.receiveAsFlow()

}