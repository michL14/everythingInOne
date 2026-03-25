package org.michorga.everythinginone.presentation.payment.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.default_description_payment
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.presentation.payment.model.CategoryUI
import org.michorga.everythinginone.presentation.payment.model.TransactionUI
import org.michorga.everythinginone.theme.AppIcons
import org.michorga.everythinginone.theme.AppIcons.IconArrowPayment
import org.michorga.everythinginone.theme.AppTheme
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes

@Composable
fun PaymentCard(
    modifier: Modifier = Modifier,
    transaction: TransactionUI
) {
    var container: Color
    var onContainer: Color
    var isRefund: Boolean
    if (transaction.categoryName == CategoryUI.REFUND.value) {
        container = colors.refund
        onContainer = colors.onRefund
        isRefund = true
    } else {
        container = colors.expense
        onContainer = colors.onExpense
        isRefund = false
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(sizes.cornerRadius),
        colors = CardDefaults.cardColors(containerColor = colors.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(sizes.paddingSmall),
        ) {
            PaymentIcon(
                container = container,
                isRefund = isRefund
            )
            DescriptionTransaction(
                modifier = Modifier.weight(1f),
                transaction = transaction
            )
            PriceComponent(
                transaction = transaction,
                onContainer = onContainer,
            )
        }
    }
}

@Composable
fun PaymentIcon(
    container: Color,
    isRefund: Boolean,
) {
    Box(
        modifier = Modifier
            .background(
                color = container,
                shape = CircleShape
            )
    ) {
        IconArrowPayment(isRefund)
    }
}

@Composable
fun DescriptionTransaction(
    modifier: Modifier,
    transaction: TransactionUI,
) {
    Column(
        modifier = modifier.padding(start = sizes.paddingSmall),
    ) {
        Text(
            modifier = Modifier,
            text = transaction.description
                ?: stringResource(Res.string.default_description_payment),
            fontWeight = Bold,
            color = colors.title,
            fontSize = sizes.fieldsSize,
        )
    }
}

@Composable
fun PriceComponent(
    transaction: TransactionUI,
    onContainer: Color,
) {
    Column(
        modifier = Modifier
            .padding(end = sizes.paddingSmall),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = "${transaction.amount}€",
            fontWeight = Bold,
            color = onContainer,
            fontSize = sizes.fieldsSize,
        )
    }
}