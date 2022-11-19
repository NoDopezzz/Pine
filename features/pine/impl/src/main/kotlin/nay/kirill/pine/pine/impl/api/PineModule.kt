package nay.kirill.pine.pine.impl.api

import nay.kirill.pine.pine.api.PineApi
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val pineModule = module {
    factoryOf(::PineApiImpl).bind<PineApi>()
}