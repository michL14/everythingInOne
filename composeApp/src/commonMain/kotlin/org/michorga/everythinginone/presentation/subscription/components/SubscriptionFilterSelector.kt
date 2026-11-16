package org.michorga.everythinginone.presentation.subscription.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.subscription_filter_all
import everythinginone.composeapp.generated.resources.subscription_filter_payed
import everythinginone.composeapp.generated.resources.subscription_filter_to_pay
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.presentation.subscription.model.SubscriptionFilter
import org.michorga.everythinginone.theme.AppTheme.sizes
import org.michorga.everythinginone.theme.filterChipCustomColors

/**
 * Rangée de filtres pour l'écran des abonnements : All / To pay / Payed.
 */
@Composable
fun SubscriptionFilterSelector(
    selectedFilter: SubscriptionFilter,
    onFilterClick: (SubscriptionFilter) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(sizes.paddingSmall),
    ) {
        SubscriptionFilter.entries.forEach { filter ->
            FilterChip(
                colors = filterChipCustomColors(),
                selected = filter == selectedFilter,
                onClick = { onFilterClick(filter) },
                label = { Text(stringResource(filter.labelRes())) }
            )
        }
    }
}

private fun SubscriptionFilter.labelRes() = when (this) {
    SubscriptionFilter.ALL -> Res.string.subscription_filter_all
    SubscriptionFilter.TO_PAY -> Res.string.subscription_filter_to_pay
    SubscriptionFilter.PAYED -> Res.string.subscription_filter_payed
}

