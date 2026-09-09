package org.michorga.everythinginone.presentation.subscription.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.michorga.everythinginone.theme.AppTheme

@Composable
fun SubscriptionCard(
    content: String,
    icon: Painter? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(AppTheme.sizes.paddingMedium),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.secondary)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            Text(text = content, color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = content, color = Color.White.copy(alpha = 0.7f), fontSize = AppTheme.sizes.bodySize)
            Text(text = content, color = Color.White.copy(alpha = 0.9f), fontSize = AppTheme.sizes.captionSize)
        }
    }
}