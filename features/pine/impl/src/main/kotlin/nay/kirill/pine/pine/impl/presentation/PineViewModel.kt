package nay.kirill.pine.pine.impl.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.server.callback.event.ServerEvent
import nay.kirill.bluetooth.server.callback.event.ServerEventCallback
import nay.kirill.core.arch.BaseEffectViewModel
import nay.kirill.pine.pine.impl.PineNavigation

internal class PineViewModel(
        private val navigation: PineNavigation,
        serverEventCallback: ServerEventCallback
) : BaseEffectViewModel<PineState, PineState, PineEffect>(
        converter = { it },
        initialState = PineState.Content
) {

    init {
        serverEventCallback.result
                .onEach { event ->
                    when (event) {
                        is ServerEvent.OnFatalException -> {
                            _effect.trySend(PineEffect.StopService)
                            state = PineState.Error
                        }
                        else -> Unit
                    }
                }
                .launchIn(viewModelScope)
    }

    fun retry() {
        state = PineState.Content
    }

    fun back() = navigation.back()

}