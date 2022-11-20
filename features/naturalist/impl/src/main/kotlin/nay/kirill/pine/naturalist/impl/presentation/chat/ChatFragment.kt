package nay.kirill.pine.naturalist.impl.presentation.chat

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

internal class ChatFragment : Fragment() {

    private val args: ChatArgs by args()

    private val viewModel: ChatViewModel by viewModel {
        parametersOf(args)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                ChatScreen(
                        state = viewModel.uiState.value,
                        onBack = viewModel::back,
                        onRetry = viewModel::retry,
                        onMessageEnter = viewModel::messageEnter,
                        onEditName = viewModel::editName,
                        onSendMessage = viewModel::sendMessage
                )
            }
        }
    }

    companion object {

        fun newInstance(args: ChatArgs) = ChatFragment().withArgs(args)

    }


}