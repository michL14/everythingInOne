package org.michorga.everythinginone.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.add_salary_amount_label
import everythinginone.composeapp.generated.resources.add_salary_cancel
import everythinginone.composeapp.generated.resources.add_salary_confirm
import everythinginone.composeapp.generated.resources.add_salary_recurring_label
import everythinginone.composeapp.generated.resources.add_salary_title
import org.jetbrains.compose.resources.stringResource

/**
 * Boîte de dialogue permettant d'ajouter le salaire du mois au Current Balance.
 * Une case à cocher permet de rendre ce montant récurrent (ajouté chaque mois).
 */
@Composable
fun AddSalaryDialog(
    onConfirm: (amount: Double, isRecurring: Boolean) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var amountText by remember { mutableStateOf("") }
    var isRecurring by remember { mutableStateOf(false) }

    val parsedAmount = amountText.replace(',', '.').toDoubleOrNull()
    val isValid = parsedAmount != null && parsedAmount > 0.0

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(Res.string.add_salary_title)) },
        text = {
            Column {
                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    singleLine = true,
                    label = { Text(stringResource(Res.string.add_salary_amount_label)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isRecurring = !isRecurring }
                ) {
                    Checkbox(
                        checked = isRecurring,
                        onCheckedChange = { isRecurring = it }
                    )
                    Text(text = stringResource(Res.string.add_salary_recurring_label))
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = isValid,
                onClick = { parsedAmount?.let { onConfirm(it, isRecurring) } }
            ) {
                Text(stringResource(Res.string.add_salary_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.add_salary_cancel))
            }
        }
    )
}

