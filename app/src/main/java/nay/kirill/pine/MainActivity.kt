package nay.kirill.pine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.server.service.ServerDataStoreKey
import nay.kirill.bluetooth.server.service.serverDataStore
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val navigatorHolder by inject<NavigatorHolder>()

    private val navigator = object : AppNavigator(this, R.id.main_container) {

        override fun setupFragmentTransaction(
                screen: FragmentScreen,
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment
        ) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }

    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)

        serverDataStore.data
                .map { it[ServerDataStoreKey.IS_SERVER_RUNNING] }
                .onEach {
                    when {
                        supportFragmentManager.findFragmentById(R.id.main_container) == null && it ?: false -> viewModel.openPine()
                        supportFragmentManager.findFragmentById(R.id.main_container) == null -> viewModel.openMain()
                        else -> Unit
                    }
                }
                .launchIn(lifecycleScope)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}