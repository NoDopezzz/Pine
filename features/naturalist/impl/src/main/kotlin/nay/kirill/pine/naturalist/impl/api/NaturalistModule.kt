package nay.kirill.pine.naturalist.impl.api

import nay.kirill.pine.naturalist.api.NaturalistApi
import nay.kirill.pine.naturalist.impl.presentation.NaturalistNavigation
import nay.kirill.pine.naturalist.impl.presentation.chat.ChatStateConverter
import nay.kirill.pine.naturalist.impl.presentation.chat.ChatViewModel
import nay.kirill.pine.naturalist.impl.presentation.connect.ConnectViewModel
import nay.kirill.pine.naturalist.impl.presentation.entername.EnterNameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val naturalistModule = module {
    factoryOf(::NaturalistApiImpl).bind<NaturalistApi>()
    factoryOf(::NaturalistNavigation)

    viewModelOf(::ConnectViewModel)
    viewModel { params ->
        EnterNameViewModel(params.get(), get(), get())
    }

    factoryOf(::ChatStateConverter)
    viewModel { param ->
        ChatViewModel(param.get(), get(), get(), get())
    }
}