package org.michorga.everythinginone.presentation.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michorga.everythinginone.presentation.home.components.MainTopBar
import org.michorga.everythinginone.presentation.home.vm.HomeViewModel
import org.michorga.everythinginone.presentation.subscription.SubscriptionSection
import org.michorga.everythinginone.theme.AppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
) {
    val colors = AppTheme.colors
    val sizes = AppTheme.sizes

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background),
        contentPadding = PaddingValues(sizes.paddingMedium)
    ) {
        /**
         * Header Section
         */

        item {
            MainTopBar(
                modifier = modifier
            )
        }

        /**
         * Balance Section
         */
        item {
            CurrentBalanceSection(
                modifier = modifier,
                onAddClick = { viewModel.navigateToAddPayment() }
            )
        }

        /**
         * Upcoming Payments Section
         */
        item {
            SubscriptionSection(modifier = Modifier)
        }

        /**
         * Recent Transactions Section
         */
        item {
            RecentTransactionSection(
                modifier = modifier,
//                items = emptyList()
            )
        }

        item { Spacer(modifier = Modifier.height(sizes.paddingLarge * 3)) }
    }
}

