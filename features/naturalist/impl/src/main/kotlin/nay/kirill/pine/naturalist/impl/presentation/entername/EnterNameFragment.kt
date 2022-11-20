package nay.kirill.pine.naturalist.impl.presentation.entername

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import nay.kirill.core.arch.withArgs
import nay.kirill.core.arch.args
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class EnterNameFragment : Fragment() {

    private val args: EnterNameArgs by args()

    private val viewModel: EnterNameViewModel by viewModel {
        parametersOf(args)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                EnterNameScreen(
                        state = viewModel.uiState.value,
                        onBack = viewModel::back,
                        onNext = viewModel::accept,
                        enterName = viewModel::enterName
                )
            }
        }
    }

    companion object {

        fun newInstance(args: EnterNameArgs) = EnterNameFragment().withArgs(args)

    }

}