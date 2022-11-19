package nay.kirill.pine.di

import nay.kirill.pine.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val mainModule = module {
    viewModelOf(::MainViewModel)
}