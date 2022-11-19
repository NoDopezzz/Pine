package nay.kirill.pine.mainmenu.impl.presentation

import androidx.lifecycle.ViewModel

internal class MainMenuViewModel(
        private val navigation: MainMenuNavigation
) : ViewModel() {

    fun onCreateSession() {
        navigation.openPine()
    }

    fun onConnectToSession() {
        navigation.openNaturalist()
    }

}
