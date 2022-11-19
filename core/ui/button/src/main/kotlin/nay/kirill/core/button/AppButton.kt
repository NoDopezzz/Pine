package nay.kirill.core.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import nay.kirill.core.compose.AppColors
import nay.kirill.core.compose.AppTextStyle

@Composable
fun AppButton(
        state: AppButtonState,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
) {
    Button(
            modifier = modifier
                    .height(44.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppColors.Primary
            ),
            shape = RoundedCornerShape(16.dp)
    ) {
        when (state) {
            is AppButtonState.Content -> Text(
                    text = state.text.uppercase(),
                    style = AppTextStyle.ButtonStyle
            )
            is AppButtonState.Loading -> CircularProgressIndicator(
                    color = AppColors.OnPrimary,
                    modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
@Preview
private fun AppButtonPreview(
        @PreviewParameter(AppButtonStateProvider::class) state: AppButtonState
) {
    AppButton(state) { }
}

internal class AppButtonStateProvider : PreviewParameterProvider<AppButtonState> {

    override val values = sequenceOf(
            AppButtonState.Loading,
            AppButtonState.Content("Создать сессию")
    )

}