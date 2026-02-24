package org.michorga.everythinginone.presentation.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.home_salutation
import everythinginone.composeapp.generated.resources.recent_transaction_title
import everythinginone.composeapp.generated.resources.see_all_label
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes

@Composable
fun RecentTransactionSection(
    modifier: Modifier = Modifier,
//    items: List<T>
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = sizes.paddingLarge, bottom = sizes.paddingSmall),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.recent_transaction_title),
            fontWeight = FontWeight.Bold,
            fontSize = sizes.subtitleSize,
            color = colors.title
        )
        Text(
            text = stringResource(Res.string.see_all_label),
            color = colors.description,
            fontSize = sizes.captionSize
        )
    }
//    items.forEach { transaction ->
//        TransactionRow(transaction)
//    }
}