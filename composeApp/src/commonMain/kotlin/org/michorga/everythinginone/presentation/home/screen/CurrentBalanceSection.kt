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
import everythinginone.composeapp.generated.resources.home_salutation
import org.jetbrains.compose.resources.stringResource
import org.michorga.everythinginone.theme.AppTheme.colors
import org.michorga.everythinginone.theme.AppTheme.sizes

@Composable
fun CurrentBalanceSection(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = sizes.paddingLarge)
            .fillMaxWidth()
            .height(130.dp)
            .background(
                brush = Brush.linearGradient(listOf(colors.secondary, colors.primary)),
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
                text = "$4,570.80",
                color = colors.onPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
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