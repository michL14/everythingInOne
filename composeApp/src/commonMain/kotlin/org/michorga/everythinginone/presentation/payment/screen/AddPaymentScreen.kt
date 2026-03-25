package org.michorga.everythinginone.presentation.payment.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.add_a_description_text_field
import everythinginone.composeapp.generated.resources.add_a_transaction
import everythinginone.composeapp.generated.resources.add_an_amount_text_field
import everythinginone.composeapp.generated.resources.bottom_sheet_success_button
import everythinginone.composeapp.generated.resources.bottom_sheet_success_message
import everythinginone.composeapp.generated.resources.payment_process
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.presentation.payment.component.CategorySelector
import org.michorga.everythinginone.presentation.payment.component.InputTextField
import org.michorga.everythinginone.presentation.payment.component.PrimaryButton
import org.michorga.everythinginone.presentation.payment.component.SuccessAnimation
import org.michorga.everythinginone.presentation.payment.component.TransactionSummaryBottomSheet
import org.michorga.everythinginone.presentation.payment.mapper.AmountErrorMapper
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel
import org.michorga.everythinginone.theme.AppIcons
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
    val categories = viewModel.categories.collectAsState().value

    LaunchedEffect(uiState.isLoading){
        viewModel.onCategorySelectedChanged(2)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = modifier,
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
                .padding(innerPadding)
                .padding(horizontal = sizes.paddingMedium)
        ) {
            Image(
                painter = painterResource(Res.drawable.payment_process),
                contentDescription = "Illustration payment"
            )

            CategorySelector(
                modifier = Modifier.padding(vertical = sizes.paddingLarge),
                categories = categories.filter { it.id != 1L },
                selectedCategory = viewModel.selectedCategoryId,
                onCategoryClick = { viewModel.onCategorySelectedChanged(it) }
            )

            InputTextField(
                modifier = Modifier.padding(top = sizes.paddingMedium),
                title = stringResource(Res.string.add_an_amount_text_field),
                text = uiState.transaction.amount,
                onTextChange = { viewModel.onAmountChange(it) },
                error = errorMessage
            )
            InputTextField(
                modifier = Modifier.padding(top = sizes.paddingMedium),
                title = stringResource(Res.string.add_a_description_text_field),
                text = uiState.transaction.description,
                onTextChange = { viewModel.onDescriptionChange(it) },
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
                    onClick = { viewModel.showBottomSheet() },
                    primaryButtonLabel = stringResource(Res.string.add_a_transaction)
                )
            }

        }

        // --- BottomSheet ---
        if (uiState.isBottomSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.hideBottomSheet() },
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
                                    viewModel.hideBottomSheet()
                                    navigateHome()
                                    viewModel.resetSuccess()
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
                            onEdit = { viewModel.hideBottomSheet() },
                            onConfirm = { viewModel.confirmTransaction() }
                        )
                    }
                }
            }
        }
    }
}