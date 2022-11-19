package nay.kirill.pine.navigation

import com.github.terrakok.cicerone.Router
import nay.kirill.pine.mainmenu.api.MainMenuApi

internal class Navigation(
        private val router: Router,
        private val mainApi: MainMenuApi
) {

    fun openMainMenu() {
        router.newRootScreen(mainApi.getMainMenuScreen())
    }

}
