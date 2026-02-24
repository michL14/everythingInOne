package org.michorga.everythinginone.presentation.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.michorga.everythinginone.theme.AppIcons
import org.michorga.everythinginone.theme.AppTheme

@Composable
fun TransactionRow() {
    val colors = AppTheme.colors
    val sizes = AppTheme.sizes

//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = sizes.paddingSmall),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        IconButton(
//            onClick = {},
//            modifier = Modifier
//        ) { AppIcons.IconButtonAdd() }
//        Box(
//            modifier = Modifier
//                .size(52.dp)
//                .background(colors.surface, RoundedCornerShape(sizes.paddingMedium)),
//            contentAlignment = Alignment.Center
//        ) {
//            IconButton(
//                onClick = {},
//                modifier = Modifier
//            ) { AppIcons.IconButtonDelete() }
//            Text(tx.name.first().toString(), fontWeight = FontWeight.Bold, color = colors.primary)
//        }
//
//        Column(
//            modifier = Modifier
//                .padding(horizontal = sizes.paddingMedium)
//                .weight(1f)
//        ) {
//            Text(
//                text = tx.name,
//                fontWeight = FontWeight.Bold,
//                fontSize = sizes.bodySize,
//                color = colors.title
//            )
//            Text(
//                text = tx.date,
//                fontSize = sizes.captionSize,
//                color = colors.description
//            )
//        }
//
//        Text(
//            text = tx.amount,
//            color = if (tx.isNegative) colors.error else Color(0xFF4CAF50),
//            fontWeight = FontWeight.Bold,
//            fontSize = sizes.bodySize
//        )
//    }
}