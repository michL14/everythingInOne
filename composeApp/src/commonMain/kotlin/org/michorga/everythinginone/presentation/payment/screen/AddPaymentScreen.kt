package org.michorga.everythinginone.presentation.payment.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.add_a_description_text_field
import everythinginone.composeapp.generated.resources.add_a_transaction
import everythinginone.composeapp.generated.resources.add_an_amount_text_field
import everythinginone.composeapp.generated.resources.bottom_sheet_success_button
import everythinginone.composeapp.generated.resources.bottom_sheet_success_message
import everythinginone.composeapp.generated.resources.payment_process
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.michorga.everythinginone.presentation.payment.component.CategorySelector
import org.michorga.everythinginone.presentation.payment.component.InputTextField
import org.michorga.everythinginone.presentation.payment.component.PrimaryButton
import org.michorga.everythinginone.presentation.payment.component.SuccessAnimation
import org.michorga.everythinginone.presentation.payment.component.TransactionSummaryBottomSheet
import org.michorga.everythinginone.presentation.payment.mapper.AmountErrorMapper
import org.michorga.everythinginone.presentation.payment.model.CategoryModelUI
import org.michorga.everythinginone.presentation.payment.model.CategoryUI
import org.michorga.everythinginone.presentation.payment.model.TransactionModel
import org.michorga.everythinginone.presentation.payment.vm.PaymentUiState
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel
import org.michorga.everythinginone.theme.AppIcons
import org.michorga.everythinginone.theme.AppTheme
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentScreen(
    modifier: Modifier = Modifier,
    viewModel: PaymentViewModel,
    onBackClick: () -> Unit,
    navigateHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val errorMessage = AmountErrorMapper.toMessage(uiState.amountError)
    // On masque les catégories "All" et "Subscription" du sélecteur.
    val categories = viewModel.categories.collectAsState().value
        .filter { it.name != CategoryUI.ALL.value && it.name != CategoryUI.SUBSCRIPTION.value }

    LaunchedEffect(categories) {
        val defaultCategory = categories.firstOrNull()
        if (defaultCategory != null && categories.none { it.id == viewModel.selectedCategoryId }) {
            viewModel.onCategorySelectedChanged(defaultCategory.id)
        }
    }

    AddPaymentScreenContent(
        modifier = modifier,
        uiState = uiState,
        categories = categories,
        selectedCategoryId = viewModel.selectedCategoryId,
        errorMessage = errorMessage,
        onBackClick = onBackClick,
        navigateHome = navigateHome,
        onCategorySelected = { viewModel.onCategorySelectedChanged(it) },
        onAmountChange = { viewModel.onAmountChange(it) },
        onDescriptionChange = { viewModel.onDescriptionChange(it) },
        onShowBottomSheet = { viewModel.showBottomSheet() },
        onHideBottomSheet = { viewModel.hideBottomSheet() },
        onConfirmTransaction = { viewModel.confirmTransaction() },
        onResetSuccess = { viewModel.resetSuccess() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddPaymentScreenContent(
    uiState: PaymentUiState,
    categories: List<CategoryModelUI>,
    selectedCategoryId: Long,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    navigateHome: () -> Unit,
    onCategorySelected: (Long) -> Unit,
    onAmountChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onShowBottomSheet: () -> Unit,
    onHideBottomSheet: () -> Unit,
    onConfirmTransaction: () -> Unit,
    onResetSuccess: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = modifier,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colors.background,
                    scrolledContainerColor = colors.background
                ),
                title = {
                    Text(
                        text = stringResource(Res.string.add_a_transaction),
                        fontSize = sizes.titleSize,
                        fontWeight = Bold,
                        color = colors.title
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        AppIcons.IconButtonBack()
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.background)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = sizes.paddingMedium)
        ) {
            Image(
                painter = painterResource(Res.drawable.payment_process),
                contentDescription = "Illustration payment"
            )

            CategorySelector(
                modifier = Modifier.padding(vertical = sizes.paddingLarge),
                categories = categories,
                selectedCategory = selectedCategoryId,
                onCategoryClick = { onCategorySelected(it) }
            )

            InputTextField(
                modifier = Modifier.padding(top = sizes.paddingMedium),
                title = stringResource(Res.string.add_an_amount_text_field),
                text = uiState.transaction.amount,
                onTextChange = { onAmountChange(it) },
                error = errorMessage,
                keyboardType = KeyboardType.Decimal
            )
            InputTextField(
                modifier = Modifier.padding(top = sizes.paddingMedium),
                title = stringResource(Res.string.add_a_description_text_field),
                text = uiState.transaction.description,
                onTextChange = { onDescriptionChange(it) },
                singleLine = false
            )
            Column(
                modifier = Modifier
                    .padding(vertical = sizes.paddingLarge)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                PrimaryButton(
                    enabled = uiState.transaction.amount.isNotEmpty() && uiState.amountError == null,
                    modifier = Modifier.padding(vertical = sizes.paddingMedium),
                    onClick = { onShowBottomSheet() },
                    primaryButtonLabel = stringResource(Res.string.add_a_transaction)
                )
            }

        }

        // --- BottomSheet ---
        if (uiState.isBottomSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { onHideBottomSheet() },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            ) {
                when {
                    uiState.isLoading -> {
                        Box(modifier = Modifier.fillMaxWidth().padding(sizes.paddingMedium), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    uiState.isSuccess -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(sizes.paddingMedium),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SuccessAnimation()
                            Text(
                                modifier = Modifier.padding(top = sizes.paddingMedium),
                                text = stringResource(Res.string.bottom_sheet_success_message),
                                style = MaterialTheme.typography.titleMedium
                            )
                            PrimaryButton(
                                onClick = {
                                    onHideBottomSheet()
                                    navigateHome()
                                    onResetSuccess()
                                },
                                modifier = Modifier
                                    .padding(top = sizes.paddingMedium),
                                primaryButtonLabel = stringResource(Res.string.bottom_sheet_success_button),
                            )
                        }
                    }
                    else -> {
                        TransactionSummaryBottomSheet(
                            transaction = uiState.transaction,
                            onEdit = { onHideBottomSheet() },
                            onConfirm = { onConfirmTransaction() }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AddPaymentScreenContentPreview() {
    AppTheme {
        AddPaymentScreenContent(
            uiState = PaymentUiState(
                transaction = TransactionModel(
                    amount = "42.00",
                    description = "Groceries",
                    isPaid = true,
                    categoryName = 2L,
                    month = 9
                )
            ),
            categories = listOf(
                CategoryModelUI(id = 1, name = "All"),
                CategoryModelUI(id = 2, name = "Subscription"),
                CategoryModelUI(id = 3, name = "Expense"),
                CategoryModelUI(id = 4, name = "Refund")
            ),
            selectedCategoryId = 2L,
            errorMessage = null,
            onBackClick = {},
            navigateHome = {},
            onCategorySelected = {},
            onAmountChange = {},
            onDescriptionChange = {},
            onShowBottomSheet = {},
            onHideBottomSheet = {},
            onConfirmTransaction = {},
            onResetSuccess = {}
        )
    }
}
