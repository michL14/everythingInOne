package org.michorga.everythinginone.presentation.subscription.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.subscription_amount_label
import everythinginone.composeapp.generated.resources.subscription_cancel
import everythinginone.composeapp.generated.resources.subscription_confirm
import everythinginone.composeapp.generated.resources.subscription_day_helper
import everythinginone.composeapp.generated.resources.subscription_day_label
import everythinginone.composeapp.generated.resources.subscription_dialog_add_title
import everythinginone.composeapp.generated.resources.subscription_dialog_edit_title
import everythinginone.composeapp.generated.resources.subscription_name_label
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.presentation.subscription.model.SubscriptionUI
import org.michorga.everythinginone.theme.AppTheme
import org.michorga.everythinginone.theme.primaryButtonCustomColors
import org.michorga.everythinginone.theme.textFieldCustomColors

/**
 * Feuille du bas (bottom sheet) pour créer ou modifier un abonnement.
 * Le champ "jour de débit" est optionnel : s'il est vide, le 1er du mois est utilisé.
 *
 * @param existing si non null, la feuille est en mode édition et pré-remplit les champs.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditSubscriptionDialog(
    existing: SubscriptionUI?,
    onConfirm: (name: String, amount: Double, dayOfMonth: Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nameText by remember { mutableStateOf(existing?.name ?: "") }
    var amountText by remember {
        mutableStateOf(existing?.amount?.toString() ?: "")
    }
    var dayText by remember {
        mutableStateOf(existing?.dayOfMonth?.toString() ?: "")
    }

    val parsedAmount = amountText.replace(',', '.').toDoubleOrNull()
    // Jour vide -> 1er du mois par défaut. Sinon doit être compris entre 1 et 31.
    val parsedDay = if (dayText.isBlank()) 1 else dayText.toIntOrNull()
    val isDayValid = parsedDay != null && parsedDay in 1..31
    val isValid = nameText.isNotBlank() && parsedAmount != null && parsedAmount > 0.0 && isDayValid

    val title = if (existing == null) {
        stringResource(Res.string.subscription_dialog_add_title)
    } else {
        stringResource(Res.string.subscription_dialog_edit_title)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = AppTheme.colors.background,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = AppTheme.sizes.paddingMedium,
                    end = AppTheme.sizes.paddingMedium,
                    bottom = AppTheme.sizes.paddingLarge
                )
        ) {
            Text(
                text = title,
                color = AppTheme.colors.title,
                fontWeight = FontWeight.Bold,
                fontSize = AppTheme.sizes.titleSize
            )

            Spacer(modifier = Modifier.height(AppTheme.sizes.paddingMedium))

            OutlinedTextField(
                value = nameText,
                onValueChange = { nameText = it },
                singleLine = true,
                label = { Text(stringResource(Res.string.subscription_name_label)) },
                colors = textFieldCustomColors(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(AppTheme.sizes.paddingSmall))
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                singleLine = true,
                label = { Text(stringResource(Res.string.subscription_amount_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                colors = textFieldCustomColors(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(AppTheme.sizes.paddingSmall))
            OutlinedTextField(
                value = dayText,
                onValueChange = { input -> dayText = input.filter { it.isDigit() }.take(2) },
                singleLine = true,
                isError = dayText.isNotBlank() && !isDayValid,
                label = { Text(stringResource(Res.string.subscription_day_label)) },
                supportingText = { Text(stringResource(Res.string.subscription_day_helper)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = textFieldCustomColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppTheme.sizes.paddingMedium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.sizes.paddingMedium)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(Res.string.subscription_cancel))
                }
                Button(
                    enabled = isValid,
                    onClick = {
                        val amount = parsedAmount ?: return@Button
                        val day = parsedDay ?: 1
                        onConfirm(nameText.trim(), amount, day)
                    },
                    colors = primaryButtonCustomColors(),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(Res.string.subscription_confirm))
                }
            }
        }
    }
}

