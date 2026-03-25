package org.michorga.everythinginone.presentation.payment.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.bottom_sheet_summary_transaction_amount
import everythinginone.composeapp.generated.resources.bottom_sheet_summary_transaction_category
import everythinginone.composeapp.generated.resources.bottom_sheet_summary_transaction_description
import everythinginone.composeapp.generated.resources.bottom_sheet_summary_transaction_modify_button
import everythinginone.composeapp.generated.resources.bottom_sheet_summary_transaction_title
import everythinginone.composeapp.generated.resources.bottom_sheet_summary_transaction_validate_button
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.data.mapper.toCategoryEnum
import org.michorga.everythinginone.presentation.payment.model.TransactionModel
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes
import org.michorga.everythinginone.theme.primaryButtonCustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionSummaryBottomSheet(
    transaction: TransactionModel,
    onConfirm: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(sizes.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(sizes.paddingMedium)
    ) {
        Text(
            text = stringResource(Res.string.bottom_sheet_summary_transaction_title),
            style = MaterialTheme.typography.titleLarge,
            color = colors.title
        )
        Text(
            text = stringResource(Res.string.bottom_sheet_summary_transaction_amount) + "${transaction.amount}€",
            style = MaterialTheme.typography.bodyLarge
        )
        if(!transaction.description.isNullOrEmpty()){
            Text(
                text = stringResource(Res.string.bottom_sheet_summary_transaction_description) + transaction.description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Text(
            text = stringResource(Res.string.bottom_sheet_summary_transaction_category)+ "${transaction.categoryName.toCategoryEnum()}",
            style = MaterialTheme.typography.bodyLarge
        )

        Row(
            modifier = Modifier.padding(top = sizes.paddingMedium),
            horizontalArrangement = Arrangement.spacedBy(sizes.paddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onEdit,
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(Res.string.bottom_sheet_summary_transaction_modify_button))
            }
            Button(
                onClick = onConfirm,
                modifier = Modifier.weight(1f),
                colors = primaryButtonCustomColors(),
            ) {
                Text(stringResource(Res.string.bottom_sheet_summary_transaction_validate_button))
            }
        }
    }
}