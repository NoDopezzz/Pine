package nay.kirill.pine.naturalist.impl.presentation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import nay.kirill.pine.naturalist.impl.presentation.chat.ChatArgs
import nay.kirill.pine.naturalist.impl.presentation.chat.ChatFragment
import nay.kirill.pine.naturalist.impl.presentation.connect.ConnectFragment
import nay.kirill.pine.naturalist.impl.presentation.entername.EnterNameArgs
import nay.kirill.pine.naturalist.impl.presentation.entername.EnterNameFragment

internal class NaturalistNavigation(
        private val router: Router
) {

    fun back() = router.exit()

    fun openChat(args: ChatArgs) {
        router.replaceScreen(FragmentScreen {
            ChatFragment.newInstance(args)
        })
    }

    fun openConnection() {
        router.replaceScreen(FragmentScreen {
            ConnectFragment.newInstance()
        })
    }

    fun openEnterName(args: EnterNameArgs) {
        router.navigateTo(FragmentScreen {
            EnterNameFragment.newInstance(args)
        })
    }

    fun replaceEnterName(args: EnterNameArgs) {
        router.replaceScreen(FragmentScreen {
            EnterNameFragment.newInstance(args)
        })
    }

}