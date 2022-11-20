package nay.kirill.pine.pine.impl.presentation

sealed interface PineState {

    object Error : PineState

    object Content : PineState

}