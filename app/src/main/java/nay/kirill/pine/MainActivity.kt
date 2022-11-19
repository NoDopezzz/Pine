package nay.kirill.pine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
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

        if (supportFragmentManager.findFragmentById(R.id.main_container) == null) {
            viewModel.openMain()
        }
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}