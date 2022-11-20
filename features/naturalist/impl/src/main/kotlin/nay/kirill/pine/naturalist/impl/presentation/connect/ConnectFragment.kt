package nay.kirill.pine.naturalist.impl.presentation.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ConnectFragment : Fragment() {

    private val viewModel: ConnectViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.startScanning()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                SessionSearchScreen(
                        state = viewModel.uiState.value,
                        onBack = viewModel::back,
                        onRetry = viewModel::startScanning
                )
            }
        }
    }

    companion object {

        fun newInstance() = ConnectFragment()

    }
}
