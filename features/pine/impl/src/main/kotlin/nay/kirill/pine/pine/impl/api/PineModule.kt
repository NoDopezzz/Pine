package nay.kirill.pine.pine.impl.api

import nay.kirill.pine.pine.api.PineApi
import nay.kirill.pine.pine.impl.PineNavigation
import nay.kirill.pine.pine.impl.presentation.PineViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val pineModule = module {
    factoryOf(::PineApiImpl).bind<PineApi>()

    factoryOf(::PineNavigation)
    factoryOf(::PineViewModel)
}