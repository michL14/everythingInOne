package org.michorga.everythinginone.presentation.subscription

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.sp

@Composable
fun SubscriptionCard(
    name: String,
    price: String,
    daysLeft: Int,
    containerColor: Color,
    icon: Painter? = null
) {
    Card(
        modifier = Modifier.width(160.dp).height(180.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
//            Image(painter = icon, contentDescription = null, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "$name Premium", color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = "$price/month", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
            Text(text = "$daysLeft days left", color = Color.White.copy(alpha = 0.9f), fontSize = 11.sp)
        }
    }
}