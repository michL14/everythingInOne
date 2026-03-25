package org.michorga.everythinginone.presentation.payment.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.payment_title
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.presentation.home.navigation.AppState
import org.michorga.everythinginone.presentation.payment.component.CategorySelector
import org.michorga.everythinginone.presentation.payment.component.PaymentCard
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel
import org.michorga.everythinginone.theme.AppIcons
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes
import org.michorga.everythinginone.theme.colorTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel,
    modifier: Modifier = Modifier,
    onAddPaymentClick: () -> Unit,
) {
    val transactions = viewModel.listOfTransactions.collectAsState().value
    val categories = viewModel.categories.collectAsState().value
    var selectedCategoryId by remember { mutableStateOf<Long>(1) }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    colors = colorTopAppBar(),
                    title = {
                        Text(
                            text = stringResource(Res.string.payment_title),
                            fontWeight = Bold,
                            color = colors.title,
                            fontSize = sizes.titleSize
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { AppState.handleBack() }) {
                            AppIcons.IconButtonBack()
                        }
                    },
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
                .background(colors.background)
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
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(colors.containerCard),
            ) {
                val filteredTransactions = if (selectedCategoryId == 1L) {
                    transactions
                } else {
                    transactions.filter {
                        it.categoryName == categories.firstOrNull { cat -> cat.id == selectedCategoryId }?.name
                    }
                }
                items(filteredTransactions) { transaction ->
                    PaymentCard(
                        modifier = Modifier.padding(sizes.paddingMedium),
                        transaction = transaction
                    )
                }
            }
        }
    }
}

