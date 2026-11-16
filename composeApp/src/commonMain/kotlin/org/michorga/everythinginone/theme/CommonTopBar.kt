package org.michorga.everythinginone.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes

@Composable
fun CommonTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    topBarTitle: String,
){
    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(sizes.paddingMedium)
        ) {
            Text(
                text = topBarTitle,
                color = colors.title,
                style = AppTheme.font.topBar,
                modifier = Modifier.align(Alignment.Center)
            )
            IconButton(onClick = onBackClick) {
                AppIcons.IconButtonBack()
            }
        }
    }
}