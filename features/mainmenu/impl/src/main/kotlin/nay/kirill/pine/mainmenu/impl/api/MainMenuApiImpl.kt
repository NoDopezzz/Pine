package nay.kirill.pine.mainmenu.impl.api

import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.pine.mainmenu.api.MainMenuApi
import nay.kirill.pine.mainmenu.impl.presentation.MainMenuFragment

internal class MainMenuApiImpl : MainMenuApi {

    override fun getMainMenuScreen(): FragmentScreen = FragmentScreen {
        MainMenuFragment.newInstance()
    }

}
