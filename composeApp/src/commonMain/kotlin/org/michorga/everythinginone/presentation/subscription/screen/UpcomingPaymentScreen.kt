package org.michorga.everythinginone.presentation.subscription.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.upcoming_payment_title
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.michorga.everythinginone.presentation.subscription.components.AddEditSubscriptionDialog
import org.michorga.everythinginone.presentation.subscription.components.DeleteSubscriptionDialog
import org.michorga.everythinginone.presentation.subscription.components.SubscriptionActionsBottomSheet
import org.michorga.everythinginone.presentation.subscription.components.SubscriptionFilterSelector
import org.michorga.everythinginone.presentation.subscription.components.SubscriptionSection
import org.michorga.everythinginone.presentation.subscription.model.SubscriptionFilter
import org.michorga.everythinginone.presentation.subscription.model.SubscriptionUI
import org.michorga.everythinginone.presentation.subscription.vm.UpcomingPaymentViewModel
import org.michorga.everythinginone.theme.AppIcons
import org.michorga.everythinginone.theme.AppTheme
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes
import org.michorga.everythinginone.theme.CommonTopBar

@Composable
fun UpcomingPaymentScreen(
    viewModel: UpcomingPaymentViewModel,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val subscriptions by viewModel.subscriptions.collectAsState()
    val selectedFilter by viewModel.filter.collectAsState()

    UpcomingPaymentScreenContent(
        modifier = modifier,
        subscriptions = subscriptions,
        selectedFilter = selectedFilter,
        onFilterChange = { viewModel.onFilterChange(it) },
        onBackClick = onBackClick,
        onSetActive = { id, active -> viewModel.setActive(id, active) },
        onMarkPaid = { id -> viewModel.markAsPaid(id) },
        onMarkUnpaid = { id -> viewModel.markAsUnpaid(id) },
        onAddSubscription = { name, amount, day -> viewModel.addSubscription(name, amount, day) },
        onUpdateSubscription = { id, name, amount, day ->
            viewModel.updateSubscription(id, name, amount, day)
        },
        onDeleteSubscription = { id -> viewModel.deleteSubscription(id) }
    )
}

@Composable
private fun UpcomingPaymentScreenContent(
    subscriptions: List<SubscriptionUI>,
    selectedFilter: SubscriptionFilter,
    modifier: Modifier = Modifier,
    onFilterChange: (SubscriptionFilter) -> Unit,
    onBackClick: () -> Unit,
    onSetActive: (Long, Boolean) -> Unit,
    onMarkPaid: (Long) -> Unit,
    onMarkUnpaid: (Long) -> Unit,
    onAddSubscription: (String, Double, Int) -> Unit,
    onUpdateSubscription: (Long, String, Double, Int) -> Unit,
    onDeleteSubscription: (Long) -> Unit,
) {
    // null = fermé, SubscriptionUI = édition, EmptyEditTarget = création
    var editTarget by remember { mutableStateOf<EditTarget?>(null) }
    var deleteTarget by remember { mutableStateOf<SubscriptionUI?>(null) }
    var menuTarget by remember { mutableStateOf<SubscriptionUI?>(null) }

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
                onClick = { editTarget = EditTarget.New },
                containerColor = colors.primary,
            ) {
                AppIcons.IconButtonAdd(
                    tint = colors.onPrimaryLight,
                )
            }
        }
    ) { innerPadding ->
        // Total des abonnements actifs, affiché sous la top bar et au-dessus des filtres.
        val activeSubscriptionsTotal = subscriptions
            .filter { it.isActive }
            .sumOf { it.amount }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(colors.background)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = sizes.paddingMedium),
                text = "Subscriptions: ${formatAmount(activeSubscriptionsTotal)}",
                color = colors.subtitle,
                fontWeight = FontWeight.Bold,
                style = AppTheme.font.topBar
            )

            SubscriptionFilterSelector(
                selectedFilter = selectedFilter,
                onFilterClick = onFilterChange,
                modifier = Modifier.padding(
                    horizontal = sizes.paddingMedium,
                    vertical = sizes.paddingSmall
                )
            )
            SubscriptionSection(
                subscriptions = subscriptions,
                onMenuClick = { menuTarget = it },
                modifier = Modifier.background(colors.background)
            )
        }
    }

    menuTarget?.let { subscription ->
        SubscriptionActionsBottomSheet(
            subscription = subscription,
            onEdit = {
                editTarget = EditTarget.Edit(subscription)
                menuTarget = null
            },
            onMarkPaid = {
                onMarkPaid(subscription.id)
                menuTarget = null
            },
            onMarkUnpaid = {
                onMarkUnpaid(subscription.id)
                menuTarget = null
            },
            onToggleActive = {
                onSetActive(subscription.id, !subscription.isActive)
                menuTarget = null
            },
            onDelete = {
                deleteTarget = subscription
                menuTarget = null
            },
            onDismiss = { menuTarget = null }
        )
    }

    editTarget?.let { target ->
        val existing = (target as? EditTarget.Edit)?.subscription
        AddEditSubscriptionDialog(
            existing = existing,
            onConfirm = { name, amount, dayOfMonth ->
                if (existing == null) {
                    onAddSubscription(name, amount, dayOfMonth)
                } else {
                    onUpdateSubscription(existing.id, name, amount, dayOfMonth)
                }
                editTarget = null
            },
            onDismiss = { editTarget = null }
        )
    }

    deleteTarget?.let { subscription ->
        DeleteSubscriptionDialog(
            subscriptionName = subscription.name,
            onConfirm = {
                onDeleteSubscription(subscription.id)
                deleteTarget = null
            },
            onDismiss = { deleteTarget = null }
        )
    }
}

@Preview
@Composable
private fun UpcomingPaymentScreenContentPreview() {
    AppTheme {
        UpcomingPaymentScreenContent(
            subscriptions = listOf(
                SubscriptionUI(
                    id = 1,
                    name = "Netflix",
                    amount = 13.49,
                    dayOfMonth = 5,
                    isActive = true,
                    nextPaymentDate = "05/10/2026",
                    isPaidThisMonth = true
                ),
                SubscriptionUI(
                    id = 2,
                    name = "Spotify",
                    amount = 9.99,
                    dayOfMonth = 12,
                    isActive = false,
                    nextPaymentDate = "12/10/2026",
                    isPaidThisMonth = false
                )
            ),
            selectedFilter = SubscriptionFilter.ALL,
            onFilterChange = {},
            onBackClick = {},
            onSetActive = { _, _ -> },
            onMarkPaid = {},
            onMarkUnpaid = {},
            onAddSubscription = { _, _, _ -> },
            onUpdateSubscription = { _, _, _, _ -> },
            onDeleteSubscription = {}
        )
    }
}

private sealed interface EditTarget {
    data object New : EditTarget
    data class Edit(val subscription: SubscriptionUI) : EditTarget
}

/** Formate un montant en chaîne avec deux décimales (ex : 1250.5 -> "1250.50"). */
private fun formatAmount(value: Double): String {
    val cents = kotlin.math.round(value * 100).toLong()
    val intPart = cents / 100
    val decPart = (cents % 100).toString().padStart(2, '0')
    return "$intPart.$decPart"
}

