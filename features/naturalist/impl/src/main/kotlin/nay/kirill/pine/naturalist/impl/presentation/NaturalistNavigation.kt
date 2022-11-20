package nay.kirill.pine.naturalist.impl.presentation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.pine.naturalist.impl.presentation.entername.EnterNameArgs
import nay.kirill.pine.naturalist.impl.presentation.entername.EnterNameFragment

internal class NaturalistNavigation(
        private val router: Router
) {

    fun back() = router.exit()

    fun openChat() {

    }

    fun replaceEnterName(args: EnterNameArgs) {
        router.replaceScreen(FragmentScreen {
            EnterNameFragment.newInstance(args)
        })
    }

}