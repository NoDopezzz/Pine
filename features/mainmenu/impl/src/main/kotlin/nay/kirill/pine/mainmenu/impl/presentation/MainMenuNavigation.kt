package nay.kirill.pine.mainmenu.impl.presentation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.pine.naturalist.api.NaturalistApi
import nay.kirill.pine.pine.api.PineApi

internal class MainMenuNavigation(
        private val router: Router,
        private val pineApi: PineApi,
        private val naturalistApi: NaturalistApi
) {

    fun openPine() {
        router.navigateTo(FragmentScreen {
            pineApi.getPineFragment()
        })
    }

    fun openNaturalist() {
        router.navigateTo(FragmentScreen {
            naturalistApi.getNaturalistFragment()
        })
    }

}
