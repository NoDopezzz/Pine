package nay.kirill.pine.mainmenu.impl.api

import nay.kirill.pine.mainmenu.api.MainMenuApi
import nay.kirill.pine.mainmenu.impl.presentation.MainMenuNavigation
import nay.kirill.pine.mainmenu.impl.presentation.MainMenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mainMenuModule = module {
    factory<MainMenuApi> { MainMenuApiImpl() }
    factoryOf(::MainMenuNavigation)
    viewModelOf(::MainMenuViewModel)
}