package nay.kirill.pine.pine.impl.presentation

import androidx.lifecycle.ViewModel
import nay.kirill.pine.pine.impl.PineNavigation

internal class PineViewModel(
        private val navigation: PineNavigation
) : ViewModel() {

    fun back() = navigation.back()

}