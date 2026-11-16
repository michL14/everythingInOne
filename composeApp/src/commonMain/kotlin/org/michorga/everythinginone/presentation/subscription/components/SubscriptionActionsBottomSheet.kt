package org.michorga.everythinginone.presentation.subscription.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Check
import compose.icons.tablericons.Edit
import compose.icons.tablericons.Eye
import compose.icons.tablericons.EyeOff
import compose.icons.tablericons.Trash
import compose.icons.tablericons.X
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.subscription_action_activate
import everythinginone.composeapp.generated.resources.subscription_action_deactivate
import everythinginone.composeapp.generated.resources.subscription_action_delete
import everythinginone.composeapp.generated.resources.subscription_action_edit
import everythinginone.composeapp.generated.resources.subscription_action_mark_paid
import everythinginone.composeapp.generated.resources.subscription_action_mark_unpaid
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.presentation.subscription.model.SubscriptionUI
import org.michorga.everythinginone.theme.AppTheme

/**
 * Feuille du bas (bottom sheet) présentant les actions disponibles pour un
 * abonnement : Éditer, Activer/Désactiver et Supprimer.
 * Remplace l'ancien menu déroulant pour un rendu plus lisible et moderne.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionActionsBottomSheet(
    subscription: SubscriptionUI,
    onEdit: () -> Unit,
    onMarkPaid: () -> Unit,
    onMarkUnpaid: () -> Unit,
    onToggleActive: () -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = subscriptionColorFor(subscription.id)

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
            // En-tête avec pastille de couleur + nom de l'abonnement.
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(accent)
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = subscription.name,
                    color = AppTheme.colors.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = AppTheme.sizes.fieldsSize
                )
            }

            Spacer(modifier = Modifier.size(AppTheme.sizes.paddingMedium))
            HorizontalDivider(color = AppTheme.colors.container.copy(alpha = 0.4f))
            Spacer(modifier = Modifier.size(AppTheme.sizes.paddingSmall))

            ActionRow(
                icon = TablerIcons.Edit,
                label = stringResource(Res.string.subscription_action_edit),
                tint = accent,
                onClick = onEdit
            )
            // Déclarer l'abonnement comme payé pour le mois en cours (débit anticipé).
            // Proposé uniquement s'il est actif et pas encore débité ce mois-ci.
            if (subscription.isActive && !subscription.isPaidThisMonth) {
                ActionRow(
                    icon = TablerIcons.Check,
                    label = stringResource(Res.string.subscription_action_mark_paid),
                    tint = accent,
                    onClick = onMarkPaid
                )
            }
            // Annuler le paiement du mois en cours si l'abonnement a déjà été débité.
            if (subscription.isPaidThisMonth) {
                ActionRow(
                    icon = TablerIcons.X,
                    label = stringResource(Res.string.subscription_action_mark_unpaid),
                    tint = accent,
                    onClick = onMarkUnpaid
                )
            }
            ActionRow(
                icon = if (subscription.isActive) TablerIcons.EyeOff else TablerIcons.Eye,
                label = if (subscription.isActive) {
                    stringResource(Res.string.subscription_action_deactivate)
                } else {
                    stringResource(Res.string.subscription_action_activate)
                },
                tint = accent,
                onClick = onToggleActive
            )
            ActionRow(
                icon = TablerIcons.Trash,
                label = stringResource(Res.string.subscription_action_delete),
                tint = AppTheme.colors.error,
                labelColor = AppTheme.colors.error,
                onClick = onDelete
            )
        }
    }
}


@Composable
private fun ActionRow(
    icon: ImageVector,
    label: String,
    tint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    labelColor: Color = AppTheme.colors.title,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppTheme.sizes.cornerRadiusSmall))
            .clickable(onClick = onClick)
            .padding(vertical = AppTheme.sizes.paddingSmall2, horizontal = AppTheme.sizes.paddingSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.size(AppTheme.sizes.paddingMedium))
        Text(
            text = label,
            color = labelColor,
            fontWeight = FontWeight.Medium,
            fontSize = AppTheme.sizes.fieldsSize
        )
    }
}

