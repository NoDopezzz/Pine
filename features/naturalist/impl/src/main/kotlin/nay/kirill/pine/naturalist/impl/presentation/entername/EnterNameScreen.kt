package nay.kirill.pine.naturalist.impl.presentation.entername

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
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
import nay.kirill.pine.naturalist.impl.R

@Composable
internal fun EnterNameScreen(
        state: EnterNameState,
        enterName: (String) -> Unit,
        onBack: () -> Unit,
        onNext: () -> Unit
) {
    Scaffold(
            topBar = {
                AppTopBar(text = stringResource(id = R.string.enter_name_title))
            }
    ) {
        Column(
                modifier = Modifier
                        .padding(bottom = it.calculateBottomPadding() + 18.dp)
                        .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                    text = stringResource(id = R.string.enter_name_subtitle),
                    style = AppTextStyle.SubTitle,
                    modifier = Modifier
                            .padding(start = 16.dp, end = 52.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                    value = state.name,
                    modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .fillMaxWidth(),
                    onValueChange = enterName,
                    maxLines = 1,
                    textStyle = AppTextStyle.SubTitleHighlighted,
                    label = {
                        Text(
                                text = stringResource(id = R.string.enter_name_label),
                                style = AppTextStyle.SubTitle
                        )
                    }
            )
            Spacer(modifier = Modifier.weight(1F))

            AppButton(
                    state = AppButtonState.Content(text = stringResource(R.string.continue_button_title)),
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    onClick = onNext
            )
            Spacer(modifier = Modifier.height(18.dp))
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
private fun EnterNameScreenPreview() {
    EnterNameScreen(
            state = EnterNameState("Имя"),
            onBack = {},
            onNext = {},
            enterName = {}
    )
}