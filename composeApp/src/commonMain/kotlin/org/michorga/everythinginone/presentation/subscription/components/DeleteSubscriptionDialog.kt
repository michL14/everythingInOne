package org.michorga.everythinginone.presentation.subscription.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.subscription_action_delete
import everythinginone.composeapp.generated.resources.subscription_cancel
import everythinginone.composeapp.generated.resources.subscription_delete_message
import everythinginone.composeapp.generated.resources.subscription_delete_title
import org.jetbrains.compose.resources.stringResource

/** Confirmation avant la suppression définitive d'un abonnement. */
@Composable
fun DeleteSubscriptionDialog(
    subscriptionName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(Res.string.subscription_delete_title)) },
        text = {
            Text(text = stringResource(Res.string.subscription_delete_message, subscriptionName))
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(Res.string.subscription_action_delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.subscription_cancel))
            }
        }
    )
}

