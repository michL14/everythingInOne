package org.michorga.everythinginone.presentation.payment.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.month_title_selector
import everythinginone.composeapp.generated.resources.recent_transaction_title
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.michorga.everythinginone.presentation.payment.component.CategorySelector
import org.michorga.everythinginone.presentation.payment.component.MonthPicker
import org.michorga.everythinginone.presentation.payment.component.PaymentCard
import org.michorga.everythinginone.presentation.payment.model.CategoryModelUI
import org.michorga.everythinginone.presentation.payment.model.Month
import org.michorga.everythinginone.presentation.payment.model.TransactionUI
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel
import org.michorga.everythinginone.theme.AppIcons
import org.michorga.everythinginone.theme.AppTheme
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onAddPaymentClick: () -> Unit,
) {
    val transactions = viewModel.listOfTransactions.collectAsState().value
    val categories = viewModel.categories.collectAsState().value

    PaymentScreenContent(
        modifier = modifier,
        transactions = transactions,
        categories = categories,
        initialSelectedMonth = viewModel.selectedMonth,
        onBackClick = onBackClick,
        onAddPaymentClick = onAddPaymentClick,
        onDeleteTransaction = viewModel::deleteTransaction
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun PaymentScreenContent(
    transactions: List<TransactionUI>,
    categories: List<CategoryModelUI>,
    initialSelectedMonth: Month,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onAddPaymentClick: () -> Unit,
    onDeleteTransaction: (Long) -> Unit = {},
) {
    var selectedCategoryId by remember { mutableStateOf<Long>(1) }
    var selectedMonth by remember { mutableStateOf(initialSelectedMonth) }

    Scaffold(
        topBar = {
            Column {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(sizes.paddingMedium)
                ) {
                    IconButton(onClick = onBackClick) {
                        AppIcons.IconButtonBack()
                    }
                    Text(
                        modifier = Modifier.align(androidx.compose.ui.Alignment.Center),
                        text = stringResource(Res.string.month_title_selector),
                        color = colors.subtitle,
                        style = AppTheme.font.topBar
                    )
                }

                MonthPicker(
                    months = Month.shortNames,
                    selectedIndex = selectedMonth.ordinal,
                    onMonthSelected = { index ->
                        selectedMonth = Month.entries[index]
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPaymentClick,
                containerColor = colors.primary,
            ) {
                AppIcons.IconButtonAdd(
                    tint = colors.onPrimaryLight,
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.containerCard)
                .padding(innerPadding)
        ) {
            CategorySelector(
                modifier = Modifier.padding(sizes.paddingMedium),
                categories = categories,
                selectedCategory = selectedCategoryId,
                onCategoryClick = { id ->
                    selectedCategoryId = id
                }
            )
            Card(
                modifier = modifier,
                shape = RoundedCornerShape(sizes.cornerRadius),
                colors = CardDefaults.cardColors(containerColor = colors.background),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    modifier = Modifier.padding(sizes.paddingMedium),
                    text = stringResource(Res.string.recent_transaction_title),
                    fontWeight = Bold,
                    color = colors.title,
                    fontSize = sizes.subtitleSize,
                )
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    val filteredTransactions = transactions.filter { transaction ->

                        val matchesCategory = if (selectedCategoryId == 1L) {
                            true
                        } else {
                            transaction.categoryName ==
                                    categories.firstOrNull { it.id == selectedCategoryId }?.name
                        }

                        val matchesMonth = transaction.month == selectedMonth.monthNumber

                        matchesCategory && matchesMonth
                    }

                    items(
                        items = filteredTransactions,
                        key = { it.id }
                    ) { transaction ->
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { value ->
                                if (value == SwipeToDismissBoxValue.StartToEnd) {
                                    onDeleteTransaction(transaction.id)
                                    true
                                } else {
                                    false
                                }
                            }
                        )

                        SwipeToDismissBox(
                            state = dismissState,
                            // Autorise uniquement le glissement vers la droite (comme sur Apple)
                            enableDismissFromStartToEnd = true,
                            enableDismissFromEndToStart = false,
                            backgroundContent = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(colors.expense)
                                        .padding(horizontal = sizes.paddingLarge),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    AppIcons.IconButtonDelete(tint = colors.onExpense)
                                }
                            }
                        ) {
                            PaymentCard(
                                modifier = Modifier
                                    .background(colors.background)
                                    .padding(sizes.paddingMedium),
                                transaction = transaction
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Preview
@Composable
private fun PaymentScreenContentPreview() {    AppTheme {
        PaymentScreenContent(
            transactions = listOf(
                TransactionUI(
                    id = 1,
                    amount = "12.99",
                    description = "Netflix",
                    categoryName = "Subscription",
                    isPaid = true,
                    month = 9
                ),
                TransactionUI(
                    id = 2,
                    amount = "45.00",
                    description = "Groceries",
                    categoryName = "Expense",
                    isPaid = true,
                    month = 9
                )
            ),
            categories = listOf(
                CategoryModelUI(id = 1, name = "All"),
                CategoryModelUI(id = 2, name = "Subscription"),
                CategoryModelUI(id = 3, name = "Expense")
            ),
            initialSelectedMonth = Month.SEPTEMBER,
            onBackClick = {},
            onAddPaymentClick = {}
        )
    }
}


