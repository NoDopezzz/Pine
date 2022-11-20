package nay.kirill.pine.pine.impl.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nay.kirill.bluetooth.server.service.BleServerService
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class PineFragment : Fragment() {

    private val viewModel: PineViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startService()

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                back()
            }
        })

        observeEffects()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                PineScreen(
                        state = viewModel.uiState.value,
                        onComplete = ::back,
                        onRetry = ::retry
                )
            }
        }
    }

    private fun observeEffects() {
        viewModel.effect
                .flowWithLifecycle(lifecycle)
                .onEach { effect ->
                    when (effect) {
                        PineEffect.StopService -> stopService()
                    }
                }
                .launchIn(lifecycleScope)
    }

    private fun retry() {
        viewModel.retry()
        startService()
    }

    private fun back() {
        viewModel.back()
        stopService()
    }

    private fun startService() {
        activity?.startService(Intent(activity, BleServerService::class.java))
    }

    private fun stopService() {
        activity?.stopService(Intent(activity, BleServerService::class.java))
    }

    companion object {

        fun newInstance() = PineFragment()

    }

}