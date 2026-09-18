package org.michorga.everythinginone.presentation.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.michorga.everythinginone.presentation.home.components.AddSalaryDialog
import org.michorga.everythinginone.presentation.home.components.MainTopBar
import org.michorga.everythinginone.presentation.home.components.RecentTransactionRow
import org.michorga.everythinginone.presentation.home.components.UpcomingPaymentCarousel
import org.michorga.everythinginone.presentation.home.model.RecentTransactionUI
import org.michorga.everythinginone.presentation.home.model.UpcomingPaymentUI
import org.michorga.everythinginone.presentation.home.vm.HomeViewModel
import org.michorga.everythinginone.theme.AppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onNavigateToAddPayment: () -> Unit,
    onNavigateToRecentTransactions: () -> Unit,
    onNavigateToUpcomingPayment: () -> Unit
) {
    val currentBalance by viewModel.currentBalance.collectAsState()
    val realEstimation by viewModel.realEstimation.collectAsState()
    val isAddSalaryDialogVisible by viewModel.isAddSalaryDialogVisible.collectAsState()
    val recentTransactions by viewModel.recentTransactions.collectAsState()
    val upcomingPayments by viewModel.upcomingPayments.collectAsState()

    HomeScreenContent(
        modifier = modifier,
        balance = currentBalance,
        realEstimation = realEstimation,
        isAddSalaryDialogVisible = isAddSalaryDialogVisible,
        recentTransactions = recentTransactions,
        upcomingPayments = upcomingPayments,
        onShowAddSalaryDialog = viewModel::showAddSalaryDialog,
        onHideAddSalaryDialog = viewModel::hideAddSalaryDialog,
        onAddSalary = { amount, isRecurring -> viewModel.addSalary(amount, isRecurring) },
        onNavigateToAddPayment = onNavigateToAddPayment,
        onNavigateToRecentTransactions = onNavigateToRecentTransactions,
        onNavigateToUpcomingPayment = onNavigateToUpcomingPayment
    )
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    balance: Double,
    realEstimation: Double,
    isAddSalaryDialogVisible: Boolean,
    recentTransactions: List<RecentTransactionUI>,
    upcomingPayments: List<UpcomingPaymentUI>,
    onShowAddSalaryDialog: () -> Unit,
    onHideAddSalaryDialog: () -> Unit,
    onAddSalary: (Double, Boolean) -> Unit,
    onNavigateToAddPayment: () -> Unit,
    onNavigateToRecentTransactions: () -> Unit,
    onNavigateToUpcomingPayment: () -> Unit
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
                modifier = Modifier,
                onAddSalaryClick = onShowAddSalaryDialog
            )
        }

        /**
         * Balance Section
         */
        item {
            CurrentBalanceSection(
                modifier = Modifier,
                balance = balance,
                realEstimation = realEstimation,
                onAddClick = onNavigateToAddPayment
            )
        }

        /**
         * Upcoming Payments Section
         */
        item {
            UpcomingPaymentSection(
                modifier = Modifier,
                onSeeAllClick = onNavigateToUpcomingPayment,
            )
        }

        /**
         * Carrousel horizontal des 3 prochains abonnements à payer (non cliquables).
         */
        item {
            UpcomingPaymentCarousel(
                payments = upcomingPayments
            )
        }

        /**
         * Recent Transactions Section
         */
        item {
            RecentTransactionSection(
                modifier = Modifier,
                onSeeAllClick = onNavigateToRecentTransactions
            )
        }

        /**
         * Liste des transactions récentes, scrollable indépendamment (hauteur fixe).
         */
        item {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sizes.paddingLarge * 20)
            ) {
                items(
                    items = recentTransactions,
                    key = { it.id }
                ) { transaction ->
                    RecentTransactionRow(transaction = transaction)
                }
            }
        }

        item {
            Spacer(
                modifier = Modifier
                    .height(sizes.paddingLarge * 3)
            )
        }
    }

    if (isAddSalaryDialogVisible) {
        AddSalaryDialog(
            onConfirm = { amount, isRecurring -> onAddSalary(amount, isRecurring) },
            onDismiss = onHideAddSalaryDialog
        )
    }
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    AppTheme {
        HomeScreenContent(
            balance = 4570.80,
            realEstimation = 4200.30,
            isAddSalaryDialogVisible = false,
            recentTransactions = listOf(
                RecentTransactionUI(
                    id = "salary-1",
                    label = "Update current balance",
                    amount = "2500.00",
                    isIncome = true,
                    month = 9
                ),
                RecentTransactionUI(
                    id = "payment-1",
                    label = "Groceries",
                    amount = "45.00",
                    isIncome = false,
                    month = 9
                )
            ),
            upcomingPayments = listOf(
                UpcomingPaymentUI(
                    id = 1,
                    name = "Netflix",
                    amount = "13.49",
                    nextPaymentDate = "05/10/2026"
                ),
                UpcomingPaymentUI(
                    id = 2,
                    name = "Spotify",
                    amount = "9.99",
                    nextPaymentDate = "12/10/2026"
                )
            ),
            onShowAddSalaryDialog = {},
            onHideAddSalaryDialog = {},
            onAddSalary = { _, _ -> },
            onNavigateToAddPayment = {},
            onNavigateToRecentTransactions = {},
            onNavigateToUpcomingPayment = {}
        )
    }
}

