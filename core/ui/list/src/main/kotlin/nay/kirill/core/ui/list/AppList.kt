package nay.kirill.core.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nay.kirill.core.compose.AppColors
import nay.kirill.core.compose.AppTextStyle

@Composable
fun <TElement : Any> AppList(
        modifier: Modifier = Modifier,
        listTitle: String,
        isLoadingVisible: Boolean = true,
        elements: List<TElement>,
        factory: @Composable (TElement) -> Unit
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(45.dp))
        Text(
                text = listTitle,
                style = AppTextStyle.ListTitleStyle
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
        ) {
            elements.forEach { element ->
                factory(element)
            }
            if (isLoadingVisible) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(
                        modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(16.dp),
                        color = AppColors.Primary,
                        strokeWidth = 2.dp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
