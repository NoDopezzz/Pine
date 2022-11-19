package nay.kirill.pine.navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val navigationModule = module {
    single { Cicerone.create() }
    factory { get<Cicerone<Router>>().router }
    factory { get<Cicerone<Router>>().getNavigatorHolder() }
    factoryOf(::Navigation)
}