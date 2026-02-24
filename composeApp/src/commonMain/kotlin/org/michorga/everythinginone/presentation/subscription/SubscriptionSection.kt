package org.michorga.everythinginone.presentation.subscription

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.see_all_label
import everythinginone.composeapp.generated.resources.upcoming_payment_title
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes

@Composable
fun SubscriptionSection(
    modifier: Modifier = Modifier
){
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = sizes.paddingSmall),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.upcoming_payment_title),
            fontWeight = FontWeight.Bold,
            fontSize = sizes.subtitleSize,
            color = colors.title
        )
        Text(text = stringResource(Res.string.see_all_label), color = colors.description, fontSize = sizes.captionSize)
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(sizes.paddingMedium),
        contentPadding = PaddingValues(vertical = sizes.paddingSmall)
    ) {
        item {
            SubscriptionCard(
                name = "Adobe",
                price = "$30",
                daysLeft = 2,
                containerColor = Color(0xFF5E43FF),
            )
        }
        item {
            SubscriptionCard(
                name = "Apple",
                price = "$30",
                daysLeft = 2,
                containerColor = colors.surface,
            )
        }
    }
}

