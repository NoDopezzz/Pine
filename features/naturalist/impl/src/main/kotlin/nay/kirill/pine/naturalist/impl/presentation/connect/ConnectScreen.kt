package nay.kirill.pine.naturalist.impl.presentation.connect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import nay.kirill.core.compose.AppTextStyle
import nay.kirill.core.topbar.AppTopBar
import nay.kirill.core.ui.error.AppError
import nay.kirill.pine.naturalist.impl.R
import nay.kirill.core.ui.res.R as CoreR

@Composable
internal fun SessionSearchScreen(
        state: ConnectState,
        onBack: () -> Unit,
        onRetry: () -> Unit
) {
    when (state) {
        is ConnectState.Content -> Content(onBack)
        is ConnectState.Error -> AppError(
                errorDescription = stringResource(id = R.string.connection_error_description),
                backAction = onBack,
                retryAction = onRetry
        )
    }
}

@Composable
private fun Content(
        onBack: () -> Unit
) {
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = R.string.connection_title))
            }
    ) {
        Column(
                modifier = Modifier
                        .padding(bottom = it.calculateBottomPadding() + 18.dp)
                        .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                    text = stringResource(id = R.string.connection_subtitle),
                    style = AppTextStyle.SubTitle,
                    modifier = Modifier
                            .padding(start = 16.dp, end = 52.dp)
            )

            Spacer(modifier = Modifier.weight(1F))
            LoadingView(
                    modifier = Modifier
                            .width(400.dp)
                            .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(1F))

            AppButton(
                    state = AppButtonState.Content(text = stringResource(CoreR.string.back_button)),
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    onClick = onBack
            )
        }
    }
}

@Composable
fun LoadingView(
        modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.connection_loading))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    LottieAnimation(
            modifier = modifier,
            composition = composition,
            progress = { progress },
    )
}

@Composable
@Preview
private fun SessionsSearchPreview(
        @PreviewParameter(SessionsSearchStateProvider::class) state: ConnectState
) {
    SessionSearchScreen(
            state = state,
            onBack = { },
            onRetry = { },
    )
}

internal class SessionsSearchStateProvider : PreviewParameterProvider<ConnectState> {

    override val values: Sequence<ConnectState> = sequenceOf(
            ConnectState.Content,
            ConnectState.Error
    )

}