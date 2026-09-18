package org.michorga.everythinginone.presentation.subscription.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.CalendarEvent
import compose.icons.tablericons.DotsVertical
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.subscription_inactive_label
import everythinginone.composeapp.generated.resources.subscription_next_payment_label
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.presentation.subscription.model.SubscriptionUI
import org.michorga.everythinginone.theme.AppTheme

@Composable
fun SubscriptionCard(
    subscription: SubscriptionUI,
    onMenuClick: () -> Unit,
) {
    // La couleur reflète l'état de paiement du mois en cours :
    // vert si l'abonnement est payé, rouge s'il ne l'est pas encore.
    val containerColor = subscriptionStatusColor(subscription.isPaidThisMonth, subscription.isActive)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable(onClick = onMenuClick),
        shape = RoundedCornerShape(AppTheme.sizes.paddingMedium),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = subscription.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = AppTheme.sizes.fieldsSize
                )
                Box {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = TablerIcons.DotsVertical,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = formatAmount(subscription.amount),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = AppTheme.sizes.subtitleSize
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (subscription.isActive) {
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
                            subscription.nextPaymentDate
                        ),
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = AppTheme.sizes.captionSize
                    )
                }
            } else {
                Text(
                    text = stringResource(Res.string.subscription_inactive_label),
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = AppTheme.sizes.captionSize
                )
            }
        }
    }
}

private fun formatAmount(amount: Double): String {
    // Format simple à deux décimales, indépendant de la plateforme.
    val rounded = (amount * 100).toLong()
    val whole = rounded / 100
    val cents = (rounded % 100).let { if (it < 0) -it else it }
    val centsStr = cents.toString().padStart(2, '0')
    return "$whole.$centsStr €"
}
