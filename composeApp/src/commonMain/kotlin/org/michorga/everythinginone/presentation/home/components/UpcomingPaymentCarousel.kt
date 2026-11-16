package org.michorga.everythinginone.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.CalendarEvent
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.subscription_next_payment_label
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.presentation.home.model.UpcomingPaymentUI
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes

/**
 * Carrousel horizontal affichant les 3 prochains abonnements à payer pour le mois
 * en cours. Les cartes ne sont pas cliquables et utilisent la couleur du thème.
 */
@Composable
fun UpcomingPaymentCarousel(
    payments: List<UpcomingPaymentUI>,
    modifier: Modifier = Modifier,
) {
    if (payments.isEmpty()) return

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = sizes.paddingSmall),
        horizontalArrangement = Arrangement.spacedBy(sizes.paddingMedium)
    ) {
        items(payments, key = { it.id }) { payment ->
            UpcomingPaymentCard(payment = payment)
        }
    }
}

@Composable
private fun UpcomingPaymentCard(payment: UpcomingPaymentUI) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(120.dp),
        shape = RoundedCornerShape(sizes.paddingMedium),
        colors = CardDefaults.cardColors(containerColor = colors.primary)
    ) {
        Column(modifier = Modifier.padding(sizes.paddingMedium)) {
            Text(
                text = payment.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = sizes.fieldsSize
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${payment.amount}€",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = sizes.subtitleSize
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = TablerIcons.CalendarEvent,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = stringResource(
                        Res.string.subscription_next_payment_label,
                        payment.nextPaymentDate
                    ),
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = sizes.captionSize
                )
            }
        }
    }
}

