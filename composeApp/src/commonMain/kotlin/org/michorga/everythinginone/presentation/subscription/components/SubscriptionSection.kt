package org.michorga.everythinginone.presentation.subscription.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michorga.everythinginone.theme.AppTheme.sizes

@Composable
fun SubscriptionSection(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(sizes.paddingMedium),
        horizontalArrangement = Arrangement.spacedBy(sizes.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(sizes.paddingMedium)
    ) {
        items(10) {
            SubscriptionCard(
                content = "Test"
            )
        }
    }
}

