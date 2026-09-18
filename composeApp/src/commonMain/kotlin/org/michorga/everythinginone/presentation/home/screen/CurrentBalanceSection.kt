package org.michorga.everythinginone.presentation.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.TablerIcons
import compose.icons.tablericons.CirclePlus
import everythinginone.composeapp.generated.resources.Res
import everythinginone.composeapp.generated.resources.current_balance_label
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes

@Composable
fun CurrentBalanceSection(
    balance: Double,
    realEstimation: Double,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = sizes.paddingLarge)
            .fillMaxWidth()
            .height(130.dp)
            .background(
                brush = Brush.linearGradient(listOf(colors.background, colors.primary)),
                shape = RoundedCornerShape(sizes.cornerRadius)
            )
            .padding(sizes.paddingLarge),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(Res.string.current_balance_label),
                color = colors.onPrimary.copy(alpha = 0.7f),
                fontSize = sizes.bodySize
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = formatCurrency(balance),
                color = colors.onPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Real estimation: ${formatCurrency(realEstimation)}",
                color = colors.onPrimary.copy(alpha = 0.7f),
                fontSize = sizes.bodySize
            )
        }
        IconButton(
            onClick = onAddClick,
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = TablerIcons.CirclePlus,
                    contentDescription = "Add",
                    tint = colors.background,
                    modifier = Modifier.size(56.dp)
                )
            }
        }
    }
}

/** Formate un montant en devise, ex: 4570.8 -> "$4,570.80". */
private fun formatCurrency(value: Double): String {
    val rounded = ((value * 100).toLong())
    val negative = rounded < 0
    val absCents = if (negative) -rounded else rounded
    val cents = (absCents % 100).toString().padStart(2, '0')
    val whole = (absCents / 100).toString()

    val grouped = StringBuilder()
    val length = whole.length
    for (i in 0 until length) {
        if (i > 0 && (length - i) % 3 == 0) grouped.append(',')
        grouped.append(whole[i])
    }
    val sign = if (negative) "-" else ""
    return "$sign\$$grouped.$cents"
}
