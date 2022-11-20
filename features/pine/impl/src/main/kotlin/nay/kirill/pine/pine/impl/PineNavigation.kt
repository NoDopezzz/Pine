package nay.kirill.pine.pine.impl

import com.github.terrakok.cicerone.Router

internal class PineNavigation(
        private val router: Router
) {

    fun back() = router.exit()

}
