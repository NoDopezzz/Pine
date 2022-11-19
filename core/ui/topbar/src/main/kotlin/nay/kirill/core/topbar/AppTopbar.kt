package nay.kirill.core.topbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nay.kirill.core.compose.AppTextStyle

@Composable
fun AppTopBar(
        modifier: Modifier = Modifier,
        text: String
) {
    Text(
        modifier = modifier
                .padding(
                    top = 40.dp,
                    start = 16.dp,
                    end = 52.dp
                ),
        text = text,
        style = AppTextStyle.Header
    )
}