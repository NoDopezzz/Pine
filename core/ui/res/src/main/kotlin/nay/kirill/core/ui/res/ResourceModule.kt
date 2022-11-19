package nay.kirill.core.ui.res

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val resourceModule = module {
    factoryOf(::ResourceProvider)
}