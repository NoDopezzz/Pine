package nay.kirill.pine.naturalist.impl.presentation.chat

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import nay.kirill.core.button.AppButton
import nay.kirill.core.button.AppButtonState
import nay.kirill.core.compose.AppColors
import nay.kirill.core.compose.AppTextStyle
import nay.kirill.core.topbar.AppTopBar
import nay.kirill.core.ui.error.AppError
import nay.kirill.pine.naturalist.impl.R

@Composable
internal fun ChatScreen(
        state: ChatUiState,
        onBack: () -> Unit,
        onRetry: () -> Unit,
        onMessageEnter: (String) -> Unit,
        onEditName: () -> Unit,
        onSendMessage: () -> Unit
) {
    when (state) {
        is ChatUiState.Error -> AppError(
                errorDescription = stringResource(id = R.string.connection_error_description),
                backAction = onBack,
                retryAction = onRetry
        )
        is ChatUiState.Content -> Content(state, onBack, onMessageEnter, onEditName, onSendMessage)
        is ChatUiState.Loading -> Loading(onBack)
    }
}

@Composable
private fun Content(
        state: ChatUiState.Content,
        onBack: () -> Unit,
        onMessageEnter: (String) -> Unit,
        onEditName: () -> Unit,
        onSendMessage: () -> Unit
) {
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = R.string.chat_title))
            }
    ) { it ->
        Column(
                modifier = Modifier
                        .padding(bottom = it.calculateBottomPadding() + 18.dp)
                        .fillMaxHeight()
        ) {
            Column(
                    modifier = Modifier.fillMaxWidth()
            ) {
                state.messages.forEach {  message ->
                    Log.i("ChatScreen", "Message: $message")
                    Text(text = message.text)
                }
            }

            Spacer(modifier = Modifier.weight(1F))

            OutlinedTextField(
                    value = state.enteredMessage,
                    modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .fillMaxWidth(),
                    onValueChange = onMessageEnter,
                    maxLines = 4,
                    textStyle = AppTextStyle.SubTitleHighlighted,
                    label = {
                        Text(
                                text = stringResource(id = R.string.enter_name_input_label),
                                style = AppTextStyle.SubTitle
                        )
                    },
                    trailingIcon = {
                        Image(
                                painter = painterResource(id = androidx.appcompat.R.drawable.abc_ic_search_api_material),
                                contentDescription = "Отправить сообщение",
                                modifier = Modifier
                                        .size(32.dp)
                                        .clickable(
                                                onClick = onSendMessage,
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = rememberRipple(
                                                        bounded = false,
                                                        color = AppColors.TransparentPrimary
                                                )
                                        )
                        )
                    }
            )
            Spacer(modifier = Modifier.height(26.dp))

            Row(
                    modifier = Modifier.fillMaxWidth()
            ) {
                AppButton(
                        state = AppButtonState.Content(text = stringResource(R.string.back_button_title)),
                        modifier = Modifier
                                .weight(1F)
                                .padding(horizontal = 20.dp),
                        onClick = onBack
                )
                AppButton(
                        state = AppButtonState.Content(text = stringResource(R.string.edit_name_button)),
                        modifier = Modifier
                                .weight(1F)
                                .padding(horizontal = 20.dp),
                        onClick = onEditName
                )
            }
        }
    }
}

@Composable
private fun Loading(
        onBack: () -> Unit
) {
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = R.string.chat_loading_title))
            }
    ) {
        Column(
                modifier = Modifier
                        .padding(bottom = it.calculateBottomPadding() + 18.dp)
                        .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.weight(1F))

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.leaf_loading))
            val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

            LottieAnimation(
                    modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.CenterHorizontally),
                    composition = composition,
                    progress = { progress },
            )

            Spacer(modifier = Modifier.weight(1F))
            AppButton(
                    state = AppButtonState.Content(text = stringResource(R.string.back_button_title)),
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    onClick = onBack
            )
        }
    }
}

@Preview
@Composable
private fun ChatScreenPreview(
        @PreviewParameter(ChatStateProvider::class) state: ChatUiState
) {
    ChatScreen(
            state = state,
            onBack = { },
            onRetry = { },
            onMessageEnter = { },
            onEditName = { },
            onSendMessage = {}
    )

}

internal class ChatStateProvider : PreviewParameterProvider<ChatUiState> {

    override val values: Sequence<ChatUiState> = sequenceOf(
            ChatUiState.Error,
            ChatUiState.Loading,
            ChatUiState.Content(enteredMessage = "", messages = listOf())
    )

}