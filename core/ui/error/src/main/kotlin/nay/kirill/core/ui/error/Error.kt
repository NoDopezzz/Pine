package nay.kirill.core.ui.error

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nay.kirill.core.button.AppButton
import nay.kirill.core.button.AppButtonState
import nay.kirill.core.compose.AppTextStyle
import nay.kirill.core.topbar.AppTopBar

@Composable
fun AppError(
        modifier: Modifier = Modifier,
        errorDescription: String,
        backAction: () -> Unit,
        retryAction: (() -> Unit)? = null
) {
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = R.string.error_screen_title))
            }
    ) { paddingValues ->
        Column(
                modifier = modifier
                        .padding(bottom = 18.dp + paddingValues.calculateBottomPadding())
                        .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                    text = errorDescription,
                    modifier = Modifier
                            .padding(start = 16.dp, end = 52.dp),
                    style = AppTextStyle.SubTitle
            )
            Spacer(modifier = Modifier.weight(1F))

            if (retryAction != null) {
                AppButton(
                        state = AppButtonState.Content(text = stringResource(id = R.string.error_screen_retry_button)),
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                        onClick = retryAction
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
            AppButton(
                    state = AppButtonState.Content(text = stringResource(id = R.string.error_screen_back_button)),
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    onClick = backAction
            )
        }
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    MaterialTheme {
        AppError(
                errorDescription = "Произошла ошибка при подключении к серверу",
                backAction = {  },
                retryAction = {  }
        )
    }
}