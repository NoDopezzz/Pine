package nay.kirill.pine.pine.impl.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nay.kirill.core.button.AppButton
import nay.kirill.core.button.AppButtonState
import nay.kirill.core.compose.AppColors
import nay.kirill.core.compose.AppTextStyle
import nay.kirill.core.topbar.AppTopBar
import nay.kirill.pine.pine.impl.R

@Composable
internal fun PineScreen(
        onComplete: () -> Unit
) {
    Scaffold(
            backgroundColor = AppColors.OnPrimary,
            topBar = {
                AppTopBar(text = stringResource(id = R.string.pine_title))
            }
    ) { paddingValues: PaddingValues ->
        Box(
                modifier = Modifier.fillMaxSize()
        ) {
            Image(
                    painter = painterResource(id = R.drawable.pine_background),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
            )
            Column(
                    modifier = Modifier
                            .padding(bottom = paddingValues.calculateBottomPadding() + 18.dp)
                            .fillMaxHeight()
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                        text = stringResource(id = R.string.pine_subtitle),
                        style = AppTextStyle.SubTitle,
                        modifier = Modifier
                                .padding(start = 16.dp, end = 52.dp)
                )
                Spacer(modifier = Modifier.weight(1F))
                AppButton(
                        state = AppButtonState.Content(text = stringResource(R.string.complete_button_title)),
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                        onClick = onComplete
                )
            }
        }
    }
}

@Preview
@Composable
private fun PineScreenPreview() {
    PineScreen(
            onComplete = {}
    )
}