package org.michorga.everythinginone.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import org.michorga.everythinginone.presentation.home.model.RecentTransactionUI
import org.michorga.everythinginone.theme.AppIcons.IconArrowPayment
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes

/**
 * Ligne d'une transaction récente sur l'écran d'accueil.
 * - Entrée d'argent : flèche verte vers le haut et montant préfixé de "+".
 * - Sortie d'argent : flèche rouge vers le bas et montant préfixé de "-".
 */
@Composable
fun RecentTransactionRow(
    modifier: Modifier = Modifier,
    transaction: RecentTransactionUI,
) {
    val container = if (transaction.isIncome) colors.refund else colors.expense
    val onContainer = if (transaction.isIncome) colors.onRefund else colors.onExpense
    val sign = if (transaction.isIncome) "+" else "-"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = sizes.paddingSmall),
        horizontalArrangement = Arrangement.spacedBy(sizes.paddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.background(color = container, shape = CircleShape)
        ) {
            // IconArrowPayment attend "isRefund" : une entrée d'argent utilise
            // la flèche verte vers le haut, une sortie la flèche rouge vers le bas.
            IconArrowPayment(isRefund = transaction.isIncome)
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = sizes.paddingSmall),
            text = transaction.label,
            fontWeight = Bold,
            color = colors.title,
            fontSize = sizes.fieldsSize,
        )
        Text(
            modifier = Modifier.padding(end = sizes.paddingSmall),
            text = "$sign${transaction.amount}€",
            fontWeight = Bold,
            color = onContainer,
            fontSize = sizes.fieldsSize,
        )
    }
}

