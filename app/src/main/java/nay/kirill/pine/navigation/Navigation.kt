package nay.kirill.pine.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.pine.mainmenu.api.MainMenuApi
import nay.kirill.pine.pine.api.PineApi

internal class Navigation(
        private val router: Router,
        private val mainApi: MainMenuApi,
        private val pineApi: PineApi
) {

    fun openMainMenu() {
        router.newRootScreen(mainApi.getMainMenuScreen())
    }

    fun openPine() {
        router.newRootChain(mainApi.getMainMenuScreen(), FragmentScreen{
            pineApi.getPineFragment()
        })
    }

}
