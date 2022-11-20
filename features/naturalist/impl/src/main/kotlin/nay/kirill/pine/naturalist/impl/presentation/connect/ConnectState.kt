package nay.kirill.pine.naturalist.impl.presentation.connect

internal sealed interface ConnectState {

    object Content : ConnectState

    object Error : ConnectState

}
