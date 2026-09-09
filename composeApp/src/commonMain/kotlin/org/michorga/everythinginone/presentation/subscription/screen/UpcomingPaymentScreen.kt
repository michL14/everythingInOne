package org.michorga.everythinginone.presentation.subscription.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.upcoming_payment_title
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.presentation.subscription.components.SubscriptionSection
import org.michorga.everythinginone.presentation.subscription.vm.UpcomingPaymentViewModel
import org.michorga.everythinginone.theme.AppIcons
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.CommonTopBar

@Composable
fun UpcomingPaymentScreen(
    viewModel: UpcomingPaymentViewModel,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Scaffold(
        containerColor = colors.background,
        topBar = {
            CommonTopBar(
                modifier = modifier,
                onBackClick = onBackClick,
                topBarTitle = stringResource(Res.string.upcoming_payment_title),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = colors.primary,
            ) {
                AppIcons.IconButtonAdd(
                    tint = colors.onPrimaryLight,
                )
            }
        }
    ) { innerPadding ->
        SubscriptionSection(
            modifier = Modifier
                .padding(innerPadding)
                .background(colors.background)
        )
    }
}

