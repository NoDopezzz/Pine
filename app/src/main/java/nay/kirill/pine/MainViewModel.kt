package nay.kirill.pine

import androidx.lifecycle.ViewModel
import nay.kirill.pine.navigation.Navigation

internal class MainViewModel(
        private val navigation: Navigation
) : ViewModel() {

    fun openMain() = navigation.openMainMenu()

    fun openPine() = navigation.openPine()

}
