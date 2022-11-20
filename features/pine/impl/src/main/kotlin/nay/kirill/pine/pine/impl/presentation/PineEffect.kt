package nay.kirill.pine.pine.impl.presentation

sealed interface PineEffect {

    object StopService : PineEffect

}