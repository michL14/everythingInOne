package org.michorga.everythinginone.presentation.subscription.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.subscription_empty_label
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.presentation.subscription.model.SubscriptionUI
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes

@Composable
fun SubscriptionSection(
    subscriptions: List<SubscriptionUI>,
    onMenuClick: (SubscriptionUI) -> Unit,
    modifier: Modifier = Modifier
) {
    if (subscriptions.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.subscription_empty_label),
                color = colors.subtitle
            )
        }
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(sizes.paddingMedium),
        horizontalArrangement = Arrangement.spacedBy(sizes.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(sizes.paddingMedium)
    ) {
        items(subscriptions, key = { it.id }) { subscription ->
            SubscriptionCard(
                subscription = subscription,
                onMenuClick = { onMenuClick(subscription) }
            )
        }
    }
}

